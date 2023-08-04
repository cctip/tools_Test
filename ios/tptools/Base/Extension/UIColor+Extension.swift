//
//  Color+Extension.swift
//  tptools
//
//  Created by fd on 2022/10/28.
//

import Foundation

extension UIColor {
  
    static func color(with lightHexString: String,
                      lightAlpha: CGFloat = 1,
                      darkHexString: String,
                      darkAlpha: CGFloat = 1) -> UIColor {
        let lightColor = UIColor(hexString: lightHexString, transparency: lightAlpha)
        let darkColor = UIColor(hexString: darkHexString, transparency: darkAlpha)
        return UIColor(.dm, light: lightColor!, dark: darkColor!)
    }

    func alpha(_ alpha: CGFloat) -> UIColor {
        return withAlphaComponent(alpha)
    }
    
    static var toolViewBGColor: UIColor {
        return .color(with: "#24262B", darkHexString: "#24262B")
    }
    
    static var baseBlack: UIColor {
        return UIColor.color(with: "#000000", darkHexString: "#000000")
    }

    static var baseWhite: UIColor {
        return UIColor.color(with: "#FFFFFF", darkHexString: "#FFFFFF")
    }
    
    
    
}
