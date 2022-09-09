package com.gs.test.data.repository

import com.gs.test.data.model.Item
import com.gs.test.data.model.Items


interface DataRepository {
    suspend fun getFromLocalSavedData(): List<Item>
    suspend fun getData(): Items
    suspend fun getFromRemoteData(): List<Item>
    suspend fun getSingleItemDetails(id: Int): Item?
}