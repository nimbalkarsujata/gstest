package com.gs.test.data.repository

import android.util.Log
import com.gs.test.data.model.Item
import com.gs.test.data.model.Items
import com.gs.test.data.repository.datasource.LocalDataSource
import com.gs.test.data.repository.datasource.RemoteDataSource
import java.util.ArrayList

class DataRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : DataRepository {
    override suspend fun getFromLocalSavedData(): List<Item> {
        lateinit var dataList: List<Item>
        try {
            dataList = localDataSource.getAllData()
            Log.d("Test", "Local data receive from database $dataList")
            if (dataList.isEmpty()) {
                dataList = remoteDataSource.getAllData().results
            }
        } catch (exception: Exception) {
            dataList = ArrayList()
            Log.i("Test", "Exception of fetch" + exception.message.toString())
        }
        return dataList
    }

    suspend fun getFromDb(): Items {
        localDataSource.getAllData().let {
            return Items(it)
        }
    }

    override suspend fun getData(): Items {
        return remoteDataSource.getAllData()
    }

    override suspend fun getFromRemoteData(): List<Item> {
        var itemsList: List<Item> = ArrayList<Item>()
        val items = remoteDataSource.getAllData()
        items.let {
            itemsList = items.results
            if (itemsList.isNotEmpty()) {
                localDataSource.clearAllData()
                localDataSource.saveAllData(itemsList)
            }
        }
        return itemsList
    }

    override suspend fun getSingleItemDetails(id: Int): Item? {
        val item = remoteDataSource.getData(id)
        if (item.body() != null) {
            return item.body()!!
        }
        return null
    }
}