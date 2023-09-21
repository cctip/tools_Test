//
//  ServerModels.swift
//  tptools
//
//  Created by admin on 2023/7/4.
//

import UIKit
import MJExtension

@objcMembers
class UserModel: NSObject {
    var avatar = ""
    var created: TimeInterval = Date().timeIntervalSince1970
    var pinChance: Int = 0
    var point: Int = 0
    var uid = ""
    var user_name = ""
    
    override class func mj_replacedKeyFromPropertyName() -> [AnyHashable : Any]! {
        return [AnyHashable : Any]()
    }
    
    override class func mj_objectClassInArray() -> [AnyHashable : Any]! {
        return [AnyHashable : Any]()
    }
}

@objcMembers
class ResultBase: NSObject {
    var code: Int = 0
    var msg = ""
    
    var isSuccess: Bool {
        get {
            return code == 10000 // success
        }
    }
}
