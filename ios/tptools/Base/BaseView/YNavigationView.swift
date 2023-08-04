//
//  YNavigationView.swift
//  tptools
//
//  Created by fd on 2022/10/18.
//

@_exported import SwifterSwift
@_exported import Then

import UIKit

enum NavigationViewTitleMode {
    case left
    case center
}

enum NavigationViewBackMode {
    case none
    case normal
}

class YNavigationView: UIView {
    var titleMode: NavigationViewTitleMode = .left {
        didSet {
            reLayout()
        }
    }
    
    var backMode: NavigationViewBackMode = .normal {
        didSet {
            reLayout()
        }
    }

    var titleLabel = {
        UILabel().then { label in
            label.textColor = .baseBlack
            label.font = .poppinsBold(with: 28)
        }
    }()

    var backButton = {
        UIButton().then { button in
            button.setImage(UIImage(named: "back"), for: .normal)
        }
    }()

    var rightButton = {
        UIButton().then { _ in
        
        }
    }()

    init() {
        super.init(frame: .zero)
        addSubviews([backButton, titleLabel, rightButton])
        backgroundColor = .baseWhite
        updateConstraints()
    }

    required init?(coder: NSCoder) {
        super.init(coder: coder)
    }

    func reLayout() {
        switch titleMode {
        case .left:
            titleLabel.textAlignment = .left
        case .center:
            titleLabel.textAlignment = .center
        }
        switch backMode {
        case .none:
            backButton.isHidden = true
        case .normal:
            backButton.isHidden = false
        }
        setNeedsLayout()
    }

    override func updateConstraints() {
        super.updateConstraints()
        backButton.snp.makeConstraints { make in
            make.size.equalTo(24)
            make.left.equalToSuperview().offset(20)
            make.centerY.equalToSuperview()
        }
        if titleMode == .left {
            titleLabel.snp.makeConstraints { make in
                make.top.bottom.equalToSuperview()
                make.left.equalTo(backButton.snp.right).offset(16)
                make.width.greaterThanOrEqualTo(40)
            }
        } else {
            titleLabel.snp.makeConstraints { make in
                make.width.greaterThanOrEqualTo(100)
                make.centerX.equalToSuperview()
                make.top.bottom.equalToSuperview()
            }
        }
        rightButton.snp.makeConstraints { make in
            make.size.equalTo(24)
            make.centerY.equalToSuperview()
            make.right.equalToSuperview().offset(-20)
        }
    }
}
