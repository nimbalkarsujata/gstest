package com.gs.test.data.repository.datasource

interface BaseDataSource<T> {
    suspend fun getAllData(): List<T>
    suspend fun saveAllData(data: List<T>)
    suspend fun clearAllData()
}