package com.gs.test.data.repository.datasource

import com.gs.test.data.database.ItemsDao
import com.gs.test.data.database.LikedItemsDao
import com.gs.test.data.model.Item
import com.gs.test.data.model.LikedItem

class LocalDataSourceImpl(private val dataDao: ItemsDao, private val likedItemsDao: LikedItemsDao) : LocalDataSource {
    override suspend fun getAllData(): List<Item> {
        return dataDao.getAll()
    }

    override suspend fun getAllLikedData(): List<LikedItem> {
        return likedItemsDao.getAll()
    }

    override suspend fun updateItemData(data: LikedItem) {
        likedItemsDao.updateRecord(data)
    }

    override suspend fun getFavoriteItemByDate(date: String): LikedItem {
       return likedItemsDao.getItemByDate(date)
    }

    override suspend fun saveAllData(data: List<Item>) {
        dataDao.insertAllRecords(data)
    }

    override suspend fun saveItemData(data: Item) {
        dataDao.insertRecord(data)
    }

    override suspend fun clearAllData() {
        dataDao.deleteAll()
    }

    override suspend fun deleteData(item: LikedItem) {
       likedItemsDao.deleteRecord(item)
    }
}