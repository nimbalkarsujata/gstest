package com.gs.test.data.repository.datasource

import com.gs.test.data.database.ItemsDao
import com.gs.test.data.model.Item

class LocalDataSourceImpl(private val dataDao: ItemsDao) : LocalDataSource {
    override suspend fun getAllData(): List<Item> {
        return dataDao.getAll()
    }

    override suspend fun saveAllData(data: List<Item>) {
        dataDao.insertAllRecords(data)
    }

    override suspend fun clearAllData() {
        dataDao.deleteAll()
    }
}