package com.gs.test.data.repository.datasource

import com.gs.test.data.model.Item
import com.gs.test.data.model.Items
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getAllData(startDate: String, endDate: String):Items
   // suspend fun getData(id :Int): Response<Item>
}