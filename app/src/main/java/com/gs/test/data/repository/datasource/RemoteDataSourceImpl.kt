package com.gs.test.data.repository.datasource

import android.util.Log
import com.gs.test.data.api.ApiService
import com.gs.test.data.model.Items

class RemoteDataSourceImpl(private val apiService: ApiService, private val apiKey: String) :
    RemoteDataSource {
    override suspend fun getAllData(startDate: String, endDate: String): Items {
        Log.d("Sujata", "Sujata From date : $startDate : $endDate")
        val data = apiService.getAllItems(apiKey = apiKey, startDate = startDate, endDate = endDate)
        data.body()?.let {
            return Items(it)
        }
        return Items(ArrayList())
    }

    //override suspend fun getData(id: Int): Response<Item> {
    // return apiService.getItemDetails(item_id = id, apiKey = apiKey)
    //}
}