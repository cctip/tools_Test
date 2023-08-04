//
//  TBAddress.swift
//  tptools
//
//  Created by admin on 2023/7/27.
//

import Foundation
import GRDB

/// TBAddress
struct TBAddress: Codable, TableRecord {
    static var databaseSelection: [SQLSelectable] = [AllColumns(), Column.rowID]
    init(row: Row) {
        id = row["id"]
        name = row["name"]
        phoneNum = row["phoneNum"]
        address = row["address"]
        city = row["city"]
        zip = row["zip"]
        country = row["country"]
        createTime = row["createTime"]
        updateTime = row["updateTime"]
    }
    init(id: Int64?,
         name: String,
         phoneNum: String,
         address: String,
         city: String,
         zip: String,
         country: String,
         createTime: TimeInterval,
         updateTime: TimeInterval
    ) {
        self.id = id
        self.name = name
        self.phoneNum = phoneNum
        self.address = address
        self.city = city
        self.zip = zip
        self.country = country
        self.createTime = createTime
        self.updateTime = updateTime
    }
    
    var id: Int64?
    var name = ""
    var phoneNum = ""
    var address = ""
    var city = ""
    var zip = ""
    var country = ""
    var createTime: TimeInterval = Date().timeIntervalSince1970
    var updateTime: TimeInterval = Date().timeIntervalSince1970
    
    private enum Columns: String, CodingKey, ColumnExpression {
        case id
        case name
        case phoneNum
        case address
        case city
        case zip
        case country
        case createTime
        case updateTime
    }
}

extension TBAddress: MutablePersistableRecord, FetchableRecord {
    public func encode(to container: inout PersistenceContainer) {
        container[Column.rowID] = id
        container["id"] = id
        container["name"] = name
        container["phoneNum"] = phoneNum
        container["address"] = address
        container["city"] = city
        container["zip"] = zip
        container["country"] = country
        container["createTime"] = createTime
        container["updateTime"] = updateTime
    }
    mutating func didInsert(with rowID: Int64, for column: String?) {
        id = rowID
        createTime = Date().timeIntervalSince1970
        updateTime = Date().timeIntervalSince1970
        TBAddress.lastInsertId = id
    }
    public static var lastInsertId: Int64?
    
    private static let dbQueue: DatabaseQueue = DBManager.dbQueue
    private static func createTable() -> Void {
        try! self.dbQueue.inDatabase({ db in
            if try db.tableExists(TableName.TBAddress) {
                return
            }
            try? db.create(table: TableName.TBAddress, temporary: false, ifNotExists: true, body: { t in
                t.column(Columns.id.rawValue, Database.ColumnType.integer).primaryKey(onConflict: .replace, autoincrement: true)
                t.column(Columns.name.rawValue, Database.ColumnType.text)
                t.column(Columns.phoneNum.rawValue, Database.ColumnType.text)
                t.column(Columns.address.rawValue, Database.ColumnType.text)
                t.column(Columns.city.rawValue, Database.ColumnType.text)
                t.column(Columns.zip.rawValue, Database.ColumnType.text)
                t.column(Columns.country.rawValue, Database.ColumnType.text)
                t.column(Columns.createTime.rawValue, Database.ColumnType.datetime)
                t.column(Columns.updateTime.rawValue, Database.ColumnType.datetime)
            })
        })
    }
    static func queryOne() -> TBAddress {
        self.createTable()
        return try! self.dbQueue.unsafeRead({ db in
            var address = TBAddress(id: nil, name: "", phoneNum: "", address: "", city: "", zip: "", country: "", createTime: Date().timeIntervalSince1970, updateTime: Date().timeIntervalSince1970)
            if let temp = try TBAddress.fetchOne(db) {
                address = temp
            } else {
                try? address.insert(db)
            }
            return address
        })
    }
    static func update(address: TBAddress) {
        self.createTable()
        try! self.dbQueue.inTransaction(.immediate, { db in
            do {
                try address.update(db)
                return Database.TransactionCompletion.commit
            } catch {
                return Database.TransactionCompletion.rollback
            }
        })
    }
}
