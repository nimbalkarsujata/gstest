package com.gs.test.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gs.test.data.model.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): ItemsDao
}