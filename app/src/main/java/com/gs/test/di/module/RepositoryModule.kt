package com.gs.test.di.module

import com.gs.test.data.repository.DataRepository
import com.gs.test.data.repository.DataRepositoryImpl
import com.gs.test.data.repository.datasource.LocalDataSource
import com.gs.test.data.repository.datasource.RemoteDataSource
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideDataRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): DataRepository {
        return DataRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource
        )
    }
}