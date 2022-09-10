package com.gs.test.di.module

import com.gs.test.data.repository.DataRepository
import com.gs.test.viewmodel.DataViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    fun provideViewModelFactory(repository: DataRepository): DataViewModelFactory {
        return DataViewModelFactory(repository = repository)
    }
}