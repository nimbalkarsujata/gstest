package com.gs.test.data.api

import com.gs.test.data.model.Item
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(APIEndPoint.GET_APOD_ENDPOINT)
    suspend fun getAllItems(
        @Query("api_key") apiKey: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): Response<List<Item>>
}