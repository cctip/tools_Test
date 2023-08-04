//
//  YBaseViewController.swift
//  tptools
//
//  Created by admin on 2022/10/18.
//

import FDFullscreenPopGesture
import UIKit
import Toast_Swift
import SnapKit
import Then
import RxSwift
import RxCocoa
import KeychainSwift
import NSObject_Rx

class YBaseViewController: UIViewController {
    var navigationView = YNavigationView()
    var isFirstAppear = true
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.addSubview(navigationView)
        fd_prefersNavigationBarHidden = true
        view.backgroundColor = .baseWhite

        navigationView.backButton.addTarget(self, action: #selector(didTapped(backButto:)), for: .touchUpInside)
        makeUI()
        makeConstraint()
        makeEvent()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(_: animated)
        if isFirstAppear {
            firstViewWillAppear(animated)
            isFirstAppear = false
        }
    }

    func firstViewWillAppear(_ animated: Bool) {
      
    }

    override func updateViewConstraints() {
        super.updateViewConstraints()
        navigationView.snp.makeConstraints { make in
            make.left.right.equalToSuperview()
            make.height.equalTo(60)
            make.top.equalTo(view.safeAreaLayoutGuide.snp.top)
        }
        makeConstraint()
    }
    
    // MARK: Private method
    @objc
    func didTapped(backButto: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    
    // MARK: Override
    func makeUI() { }
    func makeConstraint() { }
    func makeEvent() { }

    // MARK: public method
    final
    func share(text: String?, image: UIImage?, url: URL?, completion: @escaping (() -> Void)) {
        var itemsToShare = [Any]()
        if let text = text {
            itemsToShare.append(text)
        }
        if let image = image {
            itemsToShare.append(image)
        }
        if let url = url {
            itemsToShare.append(url)
        }
        let actVC = UIActivityViewController(activityItems: itemsToShare, applicationActivities: nil)
        actVC.completionWithItemsHandler = { (_, _, _, _) in
            completion()
        }
        self.present(actVC, animated: true, completion: nil)
    }
    
    final
    func showAlert(message: String) {
        self.view.makeToast(message, duration: 0.3, point: self.view.center, title: nil, image: nil) { didTap in
            self.view.hideAllToasts(includeActivity: true, clearQueue: true)
        }
    }
}
