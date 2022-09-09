package com.gs.test.data.repository.datasource

import com.gs.test.data.api.ApiService
import com.gs.test.data.model.Item
import com.gs.test.data.model.Items
import retrofit2.Response

class RemoteDataSourceImpl(private val apiService: ApiService, private val apiKey: String): RemoteDataSource {
    override suspend fun getAllData(): Items {
        val data = apiService.getAllItems(apiKey = apiKey, "2022-09-01", "2022-09-09")
        data.body()?.let {
            return Items(it)
        }
        return Items(ArrayList())
    }

    override suspend fun getData(id: Int): Response<Item> {
        return apiService.getItemDetails(item_id = id, apiKey = apiKey)
    }
}