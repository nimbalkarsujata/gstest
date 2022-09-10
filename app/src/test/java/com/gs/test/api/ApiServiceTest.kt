package com.gs.test.api

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.gs.test.BuildConfig
import com.gs.test.data.api.ApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4::class)
class ApiServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)

    }

    @Test
    fun geDataApi_apikey_Test() {
        runBlocking {
            readFileData()
            val response =
                apiService.getItemByDate(BuildConfig.API_KEY, "2022-09-01", "2022-09-09").body()
            val request = mockWebServer.takeRequest()
            Truth.assertThat(response).isNotNull()
            Truth.assertThat(request.path)
                .isEqualTo("/planetary/apod?api_key=${BuildConfig.API_KEY}&start_date=2022-09-01&end_date=2022-09-09")
        }

    }

    private fun readFileData() {
        val inputstream = javaClass.classLoader!!.getResourceAsStream("data.json")
        val source = inputstream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        mockWebServer.enqueue(mockResponse)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

}