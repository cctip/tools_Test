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
    
    func joinTimeToInterval() -> TimeInterval {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy/MM/dd"
        if let date = dateFormatter.date(from: self) {
            return date.timeIntervalSince1970
        }
        return 0
    }
    func joinTimeToEnglishName() -> String {
        var day: String = ""
        var month: String = ""
        let arr = self.components(separatedBy: "/")
        guard arr.count == 3 else { return "" }
        day = arr[2]
        let montyInt = Int(arr[1]) ?? 1
        if montyInt == 1 {
            month = "Jan"
        } else if montyInt == 2 {
            month = "Feb"
        } else if montyInt == 3 {
            month = "Mar"
        } else if montyInt == 4 {
            month = "Apr"
        } else if montyInt == 5 {
            month = "May"
        } else if montyInt == 6 {
            month = "Jun"
        } else if montyInt == 7 {
            month = "Jul"
        } else if montyInt == 8 {
            month = "Aug"
        } else if montyInt == 9 {
            month = "Sept"
        } else if montyInt == 10 {
            month = "Oct"
        } else if montyInt == 11 {
            month = "Nov"
        } else if montyInt == 12 {
            month = "Dec"
        }
        return day + "." + month
    }
}

extension TimeInterval {
    func customGetHour() -> Int {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "HH"
        let date = Date(timeIntervalSince1970: self)
        let strVal = dateFormatter.string(from: date)
        return Int(strVal) ?? 0
    }
    
    func customPointDetailTime() -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy.MM.dd HH:mm:ss"
        let date = Date(timeIntervalSince1970: self)
        return dateFormatter.string(from: date)
    }
    
    func customRedeemHistoryTime() -> String {
        return self.customPointDetailTime()
    }
    
    func customJoinTime() -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy/MM/dd"
        let date = Date(timeIntervalSince1970: self)
        let formattedString = dateFormatter.string(from: date)
        return formattedString
    }
    
    func customTaskLeftTime() -> String {
        let formatter = DateComponentsFormatter()
        formatter.allowedUnits = [.hour, .minute, .second]
        formatter.unitsStyle = .positional
        formatter.zeroFormattingBehavior = .pad
        if let resString = formatter.string(from: self) {
            let resArray = resString.components(separatedBy: ":")
            if resArray.count == 3 {
                return resArray[0] + "h:" + resArray[1] + "m:" + resArray[2] + "s"
            }
        }
        return "\(self)" + "s"
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
