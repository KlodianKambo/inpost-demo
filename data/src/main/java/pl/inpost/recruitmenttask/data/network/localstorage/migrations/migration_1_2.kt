package pl.inpost.recruitmenttask.data.network

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE shipments ADD COLUMN archived INTEGER NOT NULL DEFAULT 0")
    }
}