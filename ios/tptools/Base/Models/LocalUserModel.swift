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

@objcMembers
class LocalTaskModel: NSObject {
    var addTokenCount: Int = 0
    var viewDetailCount: Int = 0
    var quizDoneTime: String = "" // customJoinTime
    var redeemCount: Int = 0
    var pin1012Count: Int = 0
    var pin1416Count: Int = 0
    var inviteFriendCount: Int = 0
}

@objcMembers
class GiftItemModel: NSObject {
    var title: String = ""
    var worthCredit: Int = 0
    var imageName: String = ""
    var topColor: UIColor = .clear
    var fullColor: UIColor = .clear
    var bottomColor: UIColor = .clear
    
    convenience init(title: String,
                     worthCredit: Int,
                     imageName: String,
                     topColor: UIColor,
                     fullColor: UIColor,
                     bottomColor: UIColor) {
        self.init()
        self.title = title
        self.worthCredit = worthCredit
        self.imageName = imageName
        self.topColor = topColor
        self.fullColor = fullColor
        self.bottomColor = bottomColor
    }
}

enum DailyTaskItemModelType: Int {
    case signIn
    case inviteFriend
    case share
}

@objcMembers
class DailyTaskItemModel: NSObject {
    var title: String = ""
    var worthCredit: Int = 100
    var type: DailyTaskItemModelType = .signIn
    convenience init(title: String,
                     worthCredit: Int,
                     type: DailyTaskItemModelType
    ) {
        self.init()
        self.title = title
        self.worthCredit = worthCredit
        self.type = type
    }
}
