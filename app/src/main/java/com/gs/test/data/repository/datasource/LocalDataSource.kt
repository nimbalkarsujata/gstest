package com.gs.test.data.repository.datasource

import com.gs.test.data.model.Item
import com.gs.test.data.model.LikedItem

interface LocalDataSource : BaseDataSource<Item> {
    override suspend fun getAllData(): List<Item>
    override suspend fun getAllLikedData(): List<LikedItem>
    suspend fun updateItemData(date: LikedItem)
    suspend fun getFavoriteItemByDate(date: String):LikedItem
    override suspend fun saveAllData(data: List<Item>)
    override suspend fun saveItemData(data: Item)
    override suspend fun clearAllData()
    override suspend fun deleteData(item: LikedItem)
}