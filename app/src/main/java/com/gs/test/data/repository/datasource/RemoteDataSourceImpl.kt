package com.gs.test.data.repository.datasource


import com.gs.test.data.api.ApiService
import com.gs.test.data.model.Item
import com.gs.test.data.model.Items

class RemoteDataSourceImpl(private val apiService: ApiService, private val apiKey: String) :
    RemoteDataSource {
    override suspend fun getAllData(startDate: String, endDate: String): Items {
        val data =
            apiService.getItemByDate(apiKey = apiKey, startDate = startDate, endDate = endDate)
        data.body()?.let {
            return Items(it)
        }
        return Items(ArrayList())
    }

    override suspend fun getItemOnDate(date: String): Item? {
        val data = apiService.getItemByDate(apiKey = apiKey, startDate = date, endDate = date)
        data.body()?.let {
            if (it.isNotEmpty()) {
                return it[0]
            } else return null
        }
        return null
    }
}