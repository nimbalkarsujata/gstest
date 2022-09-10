package com.gs.test.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gs.test.data.model.Item
import com.gs.test.data.model.LikedItem

@Database(entities = [Item::class, LikedItem::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): ItemsDao
    abstract fun dataFavDao(): LikedItemsDao
}