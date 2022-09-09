package com.gs.test.data.repository.datasource

import com.gs.test.data.model.Item

interface LocalDataSource : BaseDataSource<Item> {
    override suspend fun getAllData(): List<Item>
    override suspend fun saveAllData(data: List<Item>)
    override suspend fun clearAllData()
}