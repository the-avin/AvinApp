package com.avin.avinapp.databases.providers

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.avin.avinapp.databases.AppDatabase
import com.avin.avinapp.utils.AppInfo

class DatabaseProviderImpl : DatabaseProvider {
    override fun provideDatabase(): AppDatabase {
        val dbFile = AppInfo.getAppDatabaseFolderFile()
        val driver = JdbcSqliteDriver("jdbc:sqlite:${dbFile.path}")
        if (dbFile.exists().not()) AppDatabase.Schema.create(driver)
        return AppDatabase(driver)
    }
}