package com.gs.test.data.repository

import com.gs.test.data.model.Item
import com.gs.test.data.model.Items


interface DataRepository {
    suspend fun getData(): Items
    suspend fun getFromRemoteData(date: String): Items
    suspend fun getDataByDate(date: String): Item?
    suspend fun updateItem(data: Item, isExist: Boolean): Items
    suspend fun getFavouriteItems(): List<Item>
}