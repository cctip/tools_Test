//
//  UIFont+Extension.swift
//  tptools
//
//  Created by fd on 2022/10/27.
//

import Foundation

extension UIFont {
    static func righteousRegular(with size: CGFloat) -> UIFont? {
        return UIFont(name: "Righteous-Regular", size: size)
    }
    static func poppinsBold(with size: CGFloat) -> UIFont? {
        return UIFont(name: "Poppins-Bold", size: size)
    }
    static func poppinsRegular(with size: CGFloat) -> UIFont? {
        return UIFont(name: "Poppins-Regular", size: size)
    }
    static func poppinsSemiBold(with size: CGFloat) -> UIFont? {
        return UIFont(name: "Poppins-SemiBold", size: size)
    }
}
