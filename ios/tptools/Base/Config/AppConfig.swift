//
//  AppConfig.swift
//  tptools
//
//  Created by admin on 2023/6/20.
//

import Foundation
import KeychainSwift
import MJExtension
import Alamofire

let networkManager = NetworkReachabilityManager(host: "www.apple.com")
struct AppConfig {
    static let baseURLForAPI = "" // server url
    static let keychainAccess = KeychainSwiftAccessOptions.accessibleAfterFirstUnlockThisDeviceOnly // keychain access
    static let kDBPassword = "Y*87*^**(dDHh!@kH123"
    
    // keychain
    static let kUserIdKeychain = "kUserIdKeychain"
    static let kASARequestDataKey = "kASARequestDataKey"
    
    // UserDefault
    static let kUserDefaultUserInfoKey = "kUserDefaultUserInfoKey"
    
    // Config
    static let appstoreAppId = "6456844723" // appleAppId
    static let appsFlyerDevKey = "gUyT294NkvpnTkQBYSDLXC" // appsflyer dev key
    static let appDownloadLink = "https://itunes.apple.com/app/id\(appstoreAppId)" // download link
}

class AppInstance: NSObject {
    public static let shared = AppInstance()
    public var appFlyerConversionInfo: [AnyHashable : Any] = [AnyHashable : Any]()
    
    private override init() {
        super.init()
    }
    
    func getASA() -> ASALocalModel {
        guard let asaData: String = KeychainSwift().get(AppConfig.kASARequestDataKey) else {
            return ASALocalModel()
        }
        let model = ASALocalModel.mj_object(withKeyValues: asaData) ?? ASALocalModel()
        return model
    }
    func setASA(_ model: ASALocalModel) {
        let jsonStr = model.mj_JSONString() ?? ""
        KeychainSwift().set(jsonStr, forKey: AppConfig.kASARequestDataKey, withAccess: AppConfig.keychainAccess)
    }
}

@objcMembers
class AppsFlyerEventModel: NSObject {
    var event: String = ""
    var params: [String: Any]?
}

@objcMembers
class ASALocalModel: NSObject {
    var isLoaded: Bool = false
    var isATT: Bool = false
    
    convenience init(isLoaded: Bool = false, isATT: Bool = false) {
        self.init()
        self.isLoaded = isLoaded
        self.isATT = isATT
    }
}
