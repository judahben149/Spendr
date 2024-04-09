package com.judahben149.spendr.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val Migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Create a new table with the updated schema
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `cashEntry_new` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`amount` REAL NOT NULL, " +
                    "`isIncome` INTEGER NOT NULL, " +
                    "`categoryId` INTEGER NOT NULL, " +
                    "`transactionDate` INTEGER NOT NULL, " +
                    "`categoryName` TEXT NOT NULL, " +
                    "`categoryIconId` INTEGER NOT NULL, " +
                    "`isIncomeCategory` INTEGER NOT NULL, " +
                    "`reason` TEXT NOT NULL DEFAULT '')"
        )

        // Copy the data from the old table to the new table
        database.execSQL(
            "INSERT INTO `cashEntry_new` " +
                    "(`id`, `amount`, `isIncome`, `categoryId`, `transactionDate`, " +
                    "`categoryName`, `categoryIconId`, `isIncomeCategory`, `reason`) " +
                    "SELECT `id`, `amount`, `isIncome`, `categoryId`, `transactionDate`, " +
                    "`categoryName`, `categoryIconId`, `isIncomeCategory`, '' " +
                    "FROM `cashEntry`"
        )

        // Remove the old table
        database.execSQL("DROP TABLE `cashEntry`")

        // Rename the new table to the old table's name
        database.execSQL("ALTER TABLE `cashEntry_new` RENAME TO `cashEntry`")
    }

}