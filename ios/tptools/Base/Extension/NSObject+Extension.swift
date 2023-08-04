//
//  NSObject+Extension.swift
//  tptools
//
//  Created by admin on 2023/7/3.
//

import UIKit
import Foundation
import KeychainSwift

extension NSObject {
    func randomIndex(max: UInt, needCount: UInt) -> [UInt] {
        guard needCount <= max else { return [UInt]() }
        var randomNumbers = Set<UInt>()
        while randomNumbers.count < needCount {
            let randomNumber = UInt(arc4random_uniform(UInt32(max)))
            randomNumbers.insert(randomNumber)
        }
        return Array(randomNumbers)
    }
    func randomUserName() -> String {
        let names = ["Emma", "Liam", "Olivia", "Noah", "Ava", "Isabella", "Sophia", "Mia", "Charlotte", "Amelia", "Harper", "Evelyn", "Abigail", "Emily", "Lily", "Grace", "Madison", "Scarlett", "Elizabeth", "Henry", "James", "Benjamin", "Alexander", "Sebastian", "Carter", "Matthew", "Samuel", "Joseph", "David", "Michael", "Daniel", "Andrew", "Lucas", "Logan", "William", "Gabriel", "Julian", "Joshua", "Elijah", "Lucy", "Oliver", "Owen", "Jackson", "Aiden", "Ethan", "Jacob", "William", "Nicholas", "Hudson", "Lincoln", "Wyatt", "Jonathan", "Theodore", "Luke", "Nathan", "Zachary", "Isaac", "Caleb", "Ezra", "Cameron", "Aaron", "Anthony", "Gavin", "Hunter", "Leonardo", "Elias", "Max", "Arthur", "Sam", "Thomas", "Connor", "Cooper", "David", "Dominic", "Isaiah", "Jack", "Jaxon", "Adam", "Adrian", "Charles", "Evan", "Ezekiel", "Jace", "Jaden", "Jasper", "Joel", "Nolan", "Peter", "Simon", "Tristan", "Xavier", "Rebecca", "Sophie", "Aria", "Chloe", "Eleanor", "Hannah", "Isabelle", "Ivy", "Mia", "Nora", "Victoria", "Zoe", "Audrey", "Bella", "Claire", "Emma", "Grace", "Hazel", "Lily", "Lucy", "Naomi", "Penelope", "Ruby", "Stella", "Violet", "Aiden", "Benjamin", "Caleb", "Daniel", "Elijah", "Henry", "Isaac", "Liam", "Matthew", "Nicholas", "Oliver", "Samuel", "Steven", "Thomas", "William", "Wyatt", "Zachary"]
        let randomNum = Int(arc4random_uniform(UInt32(names.count - 1)))
        return names[randomNum]
    }
    func randomSurveyTitle() -> String {
        let titles = [
            "Personality Insights for Pay: Unlock Your Earnings Potential!",
            "Cash in on Self-Discovery: Personality Test for Rewards!",
            "Personality Traits Payback: Earn as You Understand Yourself!",
            "Earn While You Explore: Personality Test for Rewards!",
            "Discover Your Identity: Get Paid for Personality Testing!",
            "Profit from Personalities: Earn Rewards with Every Insight!",
            "Self-Knowledge for Cash: Personality Quiz with Benefits!",
            "Personality Quiz Payday: Earn Rewards for Knowing You!",
            "Rewarding Personality Assessment: Earn with Every Response!",
            "Self-Discovery Payoff: Unlock Earnings with Personality Testing!",
            "Personality Fortunes: Unveil Your Traits, Earn Exciting Rewards!",
            "Earn with Insight: Personality Test for Personal and Financial Growth!",
            "Cash for Character: Get Rewarded for Understanding Yourself!",
            "Discover & Earn: Personality Quiz with Real-Life Benefits!",
            "Quiz Your Way to Cash: Personality Profiling for Rewards!",
            "Know Thyself, Earn Big: Personality Test with a Payoff!",
            "Unlock Rewards with Personality Insights: Test and Earn!",
            "Self-Knowledge, Paid Dividends: Earn with Your Personality!",
            "Cash In on Your Personality: Discover, Learn, and Earn!",
            "Personality Pays Off: Earn Rewards by Exploring Yourself!",
            "Personality Rewards: Find Yourself and Earn Exciting Prizes!",
            "Earn While You Reflect: Personality Test with Incentives!",
            "Uncover Your Potential: Get Paid for Personality Profiling!",
            "Personality Perks: Earn Rewards for Understanding You!",
            "Discover & Earn: Personality Test with Cash Prizes!",
            "Monetize Your Mind: Personality Quiz for Extra Income!",
            "Unlock Your Worth: Get Rewarded for Personality Insights!",
            "Personality Riches: Earn While Exploring Your Traits!",
            "Know Yourself, Earn Big: Personality Test with Bonuses!",
            "Personality Treasure Hunt: Discover, Earn, and Win!",
            "Cash for Personality Exploration: Discover, Grow, and Earn!",
            "Personality Profits: Unveil Your Traits, Reap the Rewards!",
            "Earn with Personality Insights: Quiz Your Way to Prizes!",
            "Know Your Worth: Personality Test with Real Payoff!",
            "Personality Paycheck: Get Rewarded for Self-Discovery!",
            "Unleash Your Earnings: Personality Test with Perks!",
            "Discover & Earn: Personality Quiz for Financial Gains!",
            "Unlocking Potential: Get Paid for Understanding Yourself!",
            "Personality Fortune Hunt: Explore, Learn, and Earn!",
            "Personality Incentives: Discover, Earn, and Thrive!",
            "SpendSmart: Discover Your Consumer Habits and Preferences!",
            "Consumer Behavior Insights: Earn Rewards with Every Answer!",
            "Uncover Your Spending Style: Get Paid for Knowing Yourself!",
            "Shopper's Quiz: Discover Your Buying Patterns for Cash!",
            "Cash for Consumption: Earn by Understanding Your Habits!",
            "Consumer Choices Payday: Earn Rewards for Shopping Insights!",
            "Shop & Earn: Get Paid for Your Consumer Preferences!",
            "Discover Your Spending DNA: Test and Earn Rewards!",
            "Retail Habits for Rewards: Unlock Earnings with Every Response!",
            "Consumer Traits, Consumer Rewards: Discover and Earn!",
            "Money Mindset Profiler: Get Rewarded for Your Spending Habits!",
            "Consumer Trends Payoff: Earn with Every Purchase Insight!",
            "Smart Shopper Rewards: Discover Your Spending Patterns!",
            "Cashback Consumer Quiz: Earn with Every Shopping Answer!",
            "Consumer Insights Payout: Discover, Learn, and Earn!",
            "Shop Smarter, Earn Better: Get Paid for Your Choices!",
            "Money Habits for Cash: Discover and Earn with Every Question!",
            "Consumer Survey Rewards: Unlock Earnings with Your Opinions!",
            "Savvy Shopper Payday: Earn Rewards for Your Consumption Habits!",
            "Discover Your Buying Power: Get Rewarded for Your Preferences!"
        ]
        let randomNum = Int(arc4random_uniform(UInt32(titles.count - 1)))
        return titles[randomNum]
    }
    func randomSurveyStarNum() -> Int {
        let randomNum = Int(arc4random_uniform(UInt32(5)))
        guard randomNum > 0 else { return 1 }
        return randomNum
    }
    func randomSurveyMinutes() -> Int {
        let randomNum = Int(arc4random_uniform(UInt32(15)))
        guard randomNum > 1 else { return 2 }
        return randomNum
    }
    func randomSurveyCredits() -> Int {
        let randomNum = Int(arc4random_uniform(UInt32(150)))
        guard randomNum > 10 else { return 10 }
        return randomNum
    }
    
    func userId() -> String {
        if let userId = KeychainSwift().get(AppConfig.kUserIdKeychain), userId.count > 0 {
            return userId
        }
        let newUserId = UUID().uuidString
        KeychainSwift().set(newUserId, forKey: AppConfig.kUserIdKeychain, withAccess: AppConfig.keychainAccess)
        return newUserId
    }
    func getUserInfo() -> LocalUserModel {
        if let jsonStr = UserDefaults.standard.object(forKey: AppConfig.kUserDefaultUserInfoKey) {
            if let user = LocalUserModel.mj_object(withKeyValues: jsonStr) {
                return user
            }
        }
        let newModel = LocalUserModel()
        newModel.userName = self.randomUserName()
        newModel.avatarImgName = "avatar"
        newModel.joinTime = "\(Date().timeIntervalSince1970)"
        newModel.credits = 0
        self.setUserInfo(model: newModel)
        return newModel
    }
    @discardableResult
    func setUserInfo(model: LocalUserModel) -> Bool {
        let josnStr = model.mj_JSONString()
        UserDefaults.standard.set(josnStr, forKey: AppConfig.kUserDefaultUserInfoKey)
        UserDefaults.standard.synchronize()
        return true
    }
    // Print logs
    func printLog<T>(message: T, file: String = #file, method: String = #function, line: Int = #line) {
        #if (DEBUG)
        print("\((file as NSString).lastPathComponent)[\(line)], \(method): \(message)")
        #endif
    }
    // generate surveys
    
}
