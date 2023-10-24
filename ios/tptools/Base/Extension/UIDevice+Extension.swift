//
//  UIDevice+Extension.swift
//  tptools
//
//  Created by admin on 2023/6/28.
//

import Foundation

extension UIDevice {
    static func kStatusBarH() -> CGFloat {
        let scene = UIApplication.shared.connectedScenes.first
        guard let windowScene = scene as? UIWindowScene else { return 0 }
        guard let statusBarManager = windowScene.statusBarManager else { return 0 }
        return statusBarManager.statusBarFrame.height
    }
    
    static func kSafeBottomH() -> CGFloat {
        var height: CGFloat = 0
        if let keyWindow = UIApplication.shared.windows.first(where: { $0.isKeyWindow }) {
            height = keyWindow.safeAreaInsets.bottom
        }
        return height
    }
    
    static func kScreenW() -> CGFloat {
        return UIScreen.main.bounds.width
    }
    
    static func kScreenH() -> CGFloat {
        return UIScreen.main.bounds.height
    }
}
