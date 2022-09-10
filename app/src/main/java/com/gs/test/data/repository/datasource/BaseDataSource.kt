package com.gs.test.data.repository.datasource

import com.gs.test.data.model.Item
import com.gs.test.data.model.LikedItem

interface BaseDataSource<T> {
    suspend fun getAllData(): List<T>
    suspend fun getAllLikedData(): List<LikedItem>
    suspend fun saveAllData(data: List<T>)
    suspend fun clearAllData()
    suspend fun deleteData(item: LikedItem)
    suspend fun saveItemData(data: Item)
}