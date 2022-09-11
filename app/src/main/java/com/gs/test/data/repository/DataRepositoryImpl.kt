package com.gs.test.data.repository

import com.gs.test.data.model.*
import com.gs.test.data.repository.datasource.LocalDataSource
import com.gs.test.data.repository.datasource.RemoteDataSource
import com.gs.test.ui.feature.utils.getDate

class DataRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : DataRepository {

    /**
     * Fetch data either from remote in case of failure retrive from db.
     */
    override suspend fun getData(): Items {
        return getFromRemoteData(date = getDate(latest = false))
    }

    /**
     * Get From remote server and save in database.
     * @param date: from date
     */
    override suspend fun getFromRemoteData(date: String): Items {
        val items =
            remoteDataSource.getAllData(startDate = date, endDate = getDate(latest = true))
        items.let {
            if (items.results.isNullOrEmpty().not()) {
                localDataSource.clearAllData()
                localDataSource.saveAllData(items.results)
            }
        }
        return getAllDataForDisplay()
    }

    /**
     * Get data by date
     * @param date : Date
     */
    override suspend fun getDataByDate(date: String): Item? {
        remoteDataSource.getItemOnDate(date = date)?.let {
            if (date.isEmpty()) {
                localDataSource.clearAllData()
                localDataSource.saveItemData(it)
            }
            return it
        }
        return if (!localDataSource.getAllData().isNullOrEmpty() && date.isEmpty()) {
            localDataSource.getAllData()[0]
        } else
            null
    }

    /**
     * Update favorite selection in db
     * @param data : Item
     * @param isExist: Is already exist
     */
    override suspend fun updateItem(data: Item, isExist: Boolean): Items {
        if (!isExist) {
            localDataSource.updateItemData(data.toLikedItem())
        } else {
            localDataSource.deleteData(data.toLikedItem())
        }
        return getAllDataForDisplay()
    }

    /**
     * Get list of favorite items list.
     */
    override suspend fun getFavouriteItems(): List<Item> {
        return localDataSource.getAllLikedData().map { it.toItem() }
    }

    override suspend fun getFavouriteItemByDate(date: String): Item {
       return localDataSource.getFavoriteItemByDate(date).toItem()
    }

    private suspend fun getAllDataForDisplay(): Items {
        return Items(localDataSource.getAllData())
    }
}