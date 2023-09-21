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
}

@objcMembers
class AppsFlyerEventModel: NSObject {
    var event: String = ""
    var params: [String: Any]?
}
