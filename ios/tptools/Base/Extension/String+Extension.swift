//
//  File.swift
//  tptools
//
//  Created by fd on 2022/10/27.
//

import Foundation
extension String {
    static func == (lhs: String, rhs: String?) -> Bool {
        guard let rhs else {
            return false
        }

        return lhs == rhs
    }
    
    func decimalFormat() -> String {
        let numberFormatter = NumberFormatter()
        numberFormatter.numberStyle = .decimal
        if let number = numberFormatter.number(from: self), let result = numberFormatter.string(from: number) {
            return result
        }
        return self
    }
}

extension TimeInterval {
    func getYear() -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy"
        let date = Date(timeIntervalSince1970: self)
        return dateFormatter.string(from: date)
    }
    func getMonth() -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "MM"
        let date = Date(timeIntervalSince1970: self)
        return dateFormatter.string(from: date)
    }
    func getMonthEng() -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "MM"
        let date = Date(timeIntervalSince1970: self)
        let montyInt = Int(dateFormatter.string(from: date))
        var result = ""
        if montyInt == 1 {
            result = "Jan"
        } else if montyInt == 2 {
            result = "Feb"
        } else if montyInt == 3 {
            result = "Mar"
        } else if montyInt == 4 {
            result = "Apr"
        } else if montyInt == 5 {
            result = "May"
        } else if montyInt == 6 {
            result = "Jun"
        } else if montyInt == 7 {
            result = "Jul"
        } else if montyInt == 8 {
            result = "Aug"
        } else if montyInt == 9 {
            result = "Sept"
        } else if montyInt == 10 {
            result = "Oct"
        } else if montyInt == 11 {
            result = "Nov"
        } else if montyInt == 12 {
            result = "Dec"
        }
        return result
    }
    func getDay() -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "dd"
        let date = Date(timeIntervalSince1970: self)
        return dateFormatter.string(from: date)
    }
    func getHour() -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "HH"
        let date = Date(timeIntervalSince1970: self)
        return dateFormatter.string(from: date)
    }
    func getTime() -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "HH:mm:ss"
        let date = Date(timeIntervalSince1970: self)
        return dateFormatter.string(from: date)
    }
    func customConclusionDate() -> String {
        return "\(self.getDay()), \(self.getMonthEng()), \(self.getYear()) \(self.getTime())"
    }
}

extension Optional<Bool> {
    static func == (lhs: Bool, rhs: Bool?) -> Bool {
        guard let rhs else {
            return false
        }

        return lhs == rhs
    }
}

extension Optional {
    var isNone: Bool {
        switch self {
        case .none:
            return true
        case .some:
            return false
        }
    }

    var isSome: Bool {
        return !isNone
    }

    func or(_ default: Wrapped) -> Wrapped {
        return self ?? `default`
    }

    func or(else: @autoclosure () -> Wrapped) -> Wrapped {
        return self ?? `else`()
    }

    func or(else: () -> Wrapped) -> Wrapped {
        return self ?? `else`()
    }

    func and<B>(_ optional: B?) -> B? {
        guard self != nil else { return nil }
        return optional
    }

    func and<T>(then: (Wrapped) throws -> T?) rethrows -> T? {
        guard let unwrapped = self else { return nil }
        return try then(unwrapped)
    }

    func zip2<A>(with other: Optional<A>) -> (Wrapped, A)? {
        guard let first = self, let second = other else { return nil }
        return (first, second)
    }

    func zip3<A, B>(with other: Optional<A>, another: Optional<B>) -> (Wrapped, A, B)? {
        guard let first = self,
              let second = other,
              let third = another else { return nil }
        return (first, second, third)
    }

    func filter(_ predicate: (Wrapped) -> Bool) -> Wrapped? {
        guard let unwrapped = self,
              predicate(unwrapped) else { return nil }
        return self
    }

    func expect(_ message: String) -> Wrapped {
        guard let value = self else { fatalError(message) }
        return value
    }
}

extension Bool? {
    static func == (lhs:Bool?,rhs:Bool) -> Bool {
        guard let lhs else {
            return false
        }
        return lhs == rhs
    }
}
