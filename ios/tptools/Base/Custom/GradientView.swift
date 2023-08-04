//
//  GradientView.swift
//  tptools
//
//  Created by admin on 2023/7/18.
//

import Foundation
import UIKit

public class GradientView: UIView {
    public var startColor: UIColor = .black { didSet { updateColors() }}
    public var endColor: UIColor = .white { didSet { updateColors() }}
    
    public var startPoint: CGPoint = CGPoint(x: 0.5, y: 1) { didSet { updatePoints() }}
    public var endPoint: CGPoint = CGPoint(x: 0.5, y: -2.59) { didSet { updatePoints() }}

    public var startLocation: Double = 0 { didSet { updateLocations() }}
    public var endLocation: Double = 1 { didSet { updateLocations() }}
    public var radiusMultiple: CGFloat = 0 { didSet { updateCornerRadius() } }
    
    override public class var layerClass: AnyClass { return CAGradientLayer.self }

    private var gradientLayer: CAGradientLayer { return layer as! CAGradientLayer }

    private func updateCornerRadius() {
        setNeedsDisplay()
    }
    
    private func updatePoints() {
        gradientLayer.startPoint = startPoint
        gradientLayer.endPoint = endPoint
        setNeedsDisplay()
    }
    
    private func updateLocations() {
        gradientLayer.locations = [startLocation as NSNumber, endLocation as NSNumber]
        setNeedsDisplay()
    }

    private func updateColors() {
        gradientLayer.colors = [startColor.cgColor, endColor.cgColor]
        setNeedsDisplay()
    }

    private func setup() {
        updatePoints()
        updateLocations()
        updateColors()
    }

    public override func layoutSublayers(of layer: CALayer) {
        super.layoutSublayers(of: layer)
        if radiusMultiple > 0 {
            self.layer.cornerRadius = self.bounds.width * radiusMultiple
        }
    }
    
    override public func layoutSubviews() {
        super.layoutSubviews()
        setup()
    }

    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        setup()
    }

    override private init(frame: CGRect) {
        super.init(frame: frame)
        setup()
    }
}
