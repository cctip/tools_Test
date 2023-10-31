//
//  UIButton+Extension.swift
//  CryptoShoot
//
//  Created by admin on 2023/7/10.
//

import Foundation
import UIKit

private var key: Void?
private var enlargeInsetKey: Void?

extension UIButton {
    // Add associate object
    var assoObj: Any? {
        get {
            return objc_getAssociatedObject(self, &key)
        }
        set {
            objc_setAssociatedObject(self, &key, newValue, .OBJC_ASSOCIATION_RETAIN_NONATOMIC)
        }
    }
    
    // set to enlargeInset UIButton response area
    var enlargeInset: UIEdgeInsets {
        get {
            return objc_getAssociatedObject(self, &enlargeInsetKey) as? UIEdgeInsets ?? UIEdgeInsets.zero
        }
        set {
            objc_setAssociatedObject(self, &enlargeInsetKey, newValue, .OBJC_ASSOCIATION_RETAIN_NONATOMIC)
        }
    }
    
    override open func point(inside point: CGPoint, with event: UIEvent?) -> Bool {
        if enlargeInset == .zero || !self.isEnabled || self.isHidden || self.alpha < 0.01 {
            return super.point(inside: point, with: event)
        }
        let newRect = extendRect(bounds, enlargeInset)
        return newRect.contains(point)
    }

    private func extendRect(_ rect: CGRect, _ edgeInsets: UIEdgeInsets) -> CGRect {
        let x = rect.minX - edgeInsets.left
        let y = rect.minY - edgeInsets.top
        let w = rect.width + edgeInsets.left + edgeInsets.right
        let h = rect.height + edgeInsets.top + edgeInsets.bottom
        return CGRect(x: x, y: y, width: w, height: h)
    }
}

