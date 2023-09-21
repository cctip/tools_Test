//
//  UserModel.swift
//  tptools
//
//  Created by admin on 2023/7/4.
//

import UIKit
import MJExtension

@objcMembers
class LocalUserModel: NSObject {
    var userName: String = ""
    var avatarImgName: String = ""
    var joinTime: String = "" // time interval
    var credits: Int = 0
    var surveyGenerateTime: String = ""
    var dailySignInTime: String = ""
    var dailyInviteTime: String = ""
    var dailyShareTime: String = ""
}
