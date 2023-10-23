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
import AdServices

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?
    var lView: UIView?
    var appflyerConversionInfo: BehaviorRelay<[AnyHashable : Any]?> = BehaviorRelay(value: nil)
    var isAppflyerStarted: Bool = false
    var isASALoaded: Bool = false
    
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
        rootView.backgroundColor = UIColor.bgColor

        let main = UIViewController()
        main.view.backgroundColor = UIColor.bgColor
        
        main.view = rootView
        window?.rootViewController = main
        window?.makeKeyAndVisible()
    }
}

extension AppDelegate: AppsFlyerLibDelegate {
    func onConversionDataFail(_ error: Error) {
        // check asa
        let model: ASALocalModel = AppInstance.shared.getASA()
        guard model.isLoaded == false else {
            var info: [AnyHashable : Any] = [:]
            if model.isATT {
                info["media_source"] = "normal"
            }
            DispatchQueue.main.async {
                self.appflyerConversionInfo.accept(info)
                AppInstance.shared.appFlyerConversionInfo = info
            }
            return
        }
        
        loadOrigin()
    }
    func onConversionDataSuccess(_ conversionInfo: [AnyHashable : Any]) {
        // check asa requested
        let model: ASALocalModel = AppInstance.shared.getASA()
        guard model.isLoaded == false else {
            var newInfo = conversionInfo
            if model.isATT {
                newInfo["media_source"] = "normal"
            }
            self.isAppflyerStarted = true
            DispatchQueue.main.async {
                self.appflyerConversionInfo.accept(newInfo)
                AppInstance.shared.appFlyerConversionInfo = newInfo
            }
            return
        }
        
        // request asa data
        self.requestADServiceData { res in
            // save asa request record
            AppInstance.shared.setASA(ASALocalModel(isLoaded: self.isASALoaded, isATT: res)) // set asa requested
            
            var newInfo = conversionInfo
            if res {
                newInfo["media_source"] = "normal"
            }
            self.isAppflyerStarted = true
            DispatchQueue.main.async {
                self.appflyerConversionInfo.accept(newInfo)
                AppInstance.shared.appFlyerConversionInfo = newInfo
            }
        }
    }
}

extension AppDelegate {
    func requestADServiceData(result: @escaping ((_ res: Bool) -> Void)) {
        if #available(iOS 14.3, *) {
            guard let attributionToken = try? AAAttribution.attributionToken() else {
                return result(false)
            }
            let request = NSMutableURLRequest(url: URL(string:"https://api-adservices.apple.com/api/v1/")!)
            request.httpMethod = "POST"
            request.setValue("text/plain", forHTTPHeaderField: "Content-Type")
            request.httpBody = Data(attributionToken.utf8)
            let task = URLSession.shared.dataTask(with: request as URLRequest) { (data, _, error) in
                if let _ = error {
                    return result(false)
                }
                do {
                    self.isASALoaded = true
                    let jsonRes = try JSONSerialization.jsonObject(with: data!, options: .allowFragments) as! [String: Any]
                    guard jsonRes.keys.contains("campaignId") else { return result(false) }
                    guard jsonRes.keys.contains("attribution") else { return result(false) }
                    guard let campaignId = jsonRes["campaignId"] as? Int else { return result(false) }
                    guard let isAtt = jsonRes["attribution"] as? Bool else { return result(false) }
                    let attValue: Bool = (isAtt == true && campaignId != 1234567890)
                    guard attValue == true else { return result(false) }
                    return result(true)
                } catch {
                    return result(false)
                }
            }
            task.resume()
        } else {
            self.isASALoaded = true
            result(false)
        }
    }
}

