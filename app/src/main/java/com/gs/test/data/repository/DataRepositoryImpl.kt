package com.gs.test.data.repository

import com.gs.test.data.model.*
import com.gs.test.data.repository.datasource.LocalDataSource
import com.gs.test.data.repository.datasource.RemoteDataSource
import com.gs.test.ui.feature.utils.getDate

class DataRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : DataRepository {

    override suspend fun getData(): Items {
        return getFromRemoteData(startDate = getDate(latest = false))
    }

    override suspend fun getFromRemoteData(startDate: String): Items {
        val items = remoteDataSource.getAllData(startDate = startDate, endDate = getDate(latest = true))
        items.let {
            if (items.results.isNullOrEmpty().not()) {
                localDataSource.clearAllData()
                localDataSource.saveAllData(items.results)
            }
        }
        return getAllDataForDisplay()
    }

    override suspend fun getDataByDate(date: String): Item? {
        return localDataSource.getItemByDate(date = date)
    }

    override suspend fun updateItem(data: Item, isExist: Boolean): Items {
        if (!isExist) {
            localDataSource.updateItemData(data.toLikedItem())
        } else {
            localDataSource.deleteData(data.toLikedItem())
        }
        return getAllDataForDisplay()
    }

    override suspend fun getFavouriteItems(): List<Item> {
        return localDataSource.getAllLikedData().map { it.toItem() }
    }

    private suspend fun getAllDataForDisplay(): Items {
        return Items(localDataSource.getAllData())
    }
}