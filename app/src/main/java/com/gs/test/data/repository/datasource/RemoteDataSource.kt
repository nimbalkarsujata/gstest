package com.gs.test.data.repository.datasource

import com.gs.test.data.model.Item
import com.gs.test.data.model.Items

interface RemoteDataSource {
    suspend fun getAllData(startDate: String, endDate: String): Items
    suspend fun getItemOnDate(date: String): Item?
}