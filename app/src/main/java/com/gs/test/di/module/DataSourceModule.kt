package com.gs.test.di.module

import com.gs.test.BuildConfig
import com.gs.test.data.api.ApiService
import com.gs.test.data.database.ItemsDao
import com.gs.test.data.repository.datasource.LocalDataSource
import com.gs.test.data.repository.datasource.LocalDataSourceImpl
import com.gs.test.data.repository.datasource.RemoteDataSource
import com.gs.test.data.repository.datasource.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides

@Module
class DataSourceModule {

    @Provides
    fun provideLocalDataSource(datasDao: ItemsDao): LocalDataSource {
        return LocalDataSourceImpl(dataDao = datasDao)
    }

    @Provides
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource {
        return RemoteDataSourceImpl(apiService = apiService, apiKey = BuildConfig.API_KEY)
    }
}