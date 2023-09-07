//
//  SSDBManager.swift
//  tptools
//
//  Created by admin on 2022/4/26.
//

import GRDB

struct DataBaseName {
    static let walletDB = "loc.db"
}

struct TableName {
    static let TBAddress = "TBAddress"
}

public class DBManager: NSObject {
    private static var dbPath: String = {
        let filePath: String = NSSearchPathForDirectoriesInDomains(FileManager.SearchPathDirectory.documentDirectory, FileManager.SearchPathDomainMask.userDomainMask, true).first!.appending("/\(DataBaseName.walletDB)")
        return filePath
    }()
    
    private static var configuaration: Configuration = {
        var configuration = Configuration()
        configuration.prepareDatabase { db in
            // try db.usePassphrase(AppConfig.kDBPassword) // Set password
        }
        configuration.busyMode = Database.BusyMode.timeout(5.0)
        return configuration
    }()
    
    public static var dbQueue: DatabaseQueue = {
        let db = try! DatabaseQueue(path: DBManager.dbPath, configuration: DBManager.configuaration)
        db.releaseMemory()
        return db
    }()
    
    /// Migrate DB to new
    public static func migrateDBToNewVersion() {
        let migrator = DatabaseMigrator()
        // Migration list add here
        /* example
        migrator.registerMigration("CreateAuthor") { db in
            try db.create(table: "Author") { t in
                t.autoIncrementedPrimaryKey("id")
                t.column("createTime", .datetime)
                t.column("name", .text).notNull()
            }
        }
        */
        try? migrator.migrate(DBManager.dbQueue)
    }
}
