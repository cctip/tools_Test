//
//  AppDelegate.swift
//  tptools
//
//  Created by admin on 2023/8/4.
//

import UIKit
import FluentDarkModeKit
import KeychainSwift
import IQKeyboardManagerSwift
import AppsFlyerLib
import RxSwift
import RxRelay
import Alamofire

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?
    var lView: UIView?
    var appflyerConversionInfo: BehaviorRelay<[AnyHashable : Any]?> = BehaviorRelay(value: nil)
    var isAppflyerStarted: Bool = false
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // dark mode
        let configuration = DMEnvironmentConfiguration()
        configuration.themeChangeHandler = {}
        DarkModeManager.setup(with: configuration)
        DarkModeManager.register(with: UIApplication.shared)
        // keyboard
        IQKeyboardManager.shared.enable = true
        // AppsFlyer
        AppsFlyerLib.shared().appsFlyerDevKey = AppConfig.appsFlyerDevKey
        AppsFlyerLib.shared().appleAppID = AppConfig.appstoreAppId
        AppsFlyerLib.shared().isDebug = true
        AppsFlyerLib.shared().delegate = self
        // default window
        window = UIWindow(frame: UIScreen.main.bounds)
        if let launch = UIStoryboard(name: "LaunchScreen", bundle: nil).instantiateInitialViewController() {
            launch.view.frame = UIScreen.main.bounds
            lView = launch.view
            window?.rootViewController = UIViewController()
            window?.addSubview(launch.view)
            window!.makeKeyAndVisible()
        }
        // appsflyer
        appflyerConversionInfo.subscribe(onNext: { [weak self] val in
            guard let self = self else { return }
            guard let _ = val else { return }
            loadRN()
            lView?.removeFromSuperview()
        }).disposed(by: rx.disposeBag)
        
        // network
        networkManager?.startListening(onUpdatePerforming: { status in
            let reachAble: Bool = status == .reachable(.cellular) || status == .reachable(.ethernetOrWiFi)
            if reachAble && self.isAppflyerStarted == false {
                AppsFlyerLib.shared().start()
            } else {
                DispatchQueue.main.async {
                    if !reachAble && self.isAppflyerStarted == false {
                        self.loadOrigin()
                    }
                }
            }
        })
        return true
    }
    func application(_ application: UIApplication, continue userActivity: NSUserActivity, restorationHandler: @escaping ([UIUserActivityRestoring]?) -> Void) -> Bool {
        var conversionInfo: [AnyHashable : Any] = [:]
        conversionInfo["media_source"] = "normal"
        AppInstance.shared.appFlyerConversionInfo = conversionInfo
        loadRN()
        return true
    }
    
    func loadOrigin() {
         let main = HomeViewController()
         let navi = YNavigationController(rootViewController: main)
         navi.navigationBar.isHidden = true
         main.fd_prefersNavigationBarHidden = true
         window?.rootViewController = navi
         window!.makeKeyAndVisible()
    }
    func loadRN() {
        var rnUrl: URL!
        #if DEBUG
            rnUrl = RCTBundleURLProvider.sharedSettings().jsBundleURL(forBundleRoot: "index", fallbackExtension: nil)
        #else
            rnUrl = Bundle.main.url(forResource: "main", withExtension: "jsbundle")
        #endif
        let rootView = RCTRootView(bundleURL: rnUrl, moduleName: "MyApp1", initialProperties: nil)
        rootView.backgroundColor = UIColor.toolViewBGColor

        let main = UIViewController()
        main.view.backgroundColor = UIColor.toolViewBGColor
        
        main.view = rootView
        window?.rootViewController = main
        window?.makeKeyAndVisible()
    }
}

extension AppDelegate: AppsFlyerLibDelegate {
    func onConversionDataFail(_ error: Error) {
        loadOrigin()
    }
    func onConversionDataSuccess(_ conversionInfo: [AnyHashable : Any]) {
        self.isAppflyerStarted = true
        appflyerConversionInfo.accept(conversionInfo)
        AppInstance.shared.appFlyerConversionInfo = conversionInfo
    }
}

