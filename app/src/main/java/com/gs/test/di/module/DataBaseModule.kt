package com.gs.test.di.module

import android.content.Context
import androidx.room.Room
import com.gs.test.data.database.AppDatabase
import com.gs.test.data.database.ItemsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Singleton
    @Provides
    fun provideDataBase(context: Context): AppDatabase{
        return Room.databaseBuilder(context,AppDatabase::class.java,"compose_database").build()
    }

    @Singleton
    @Provides
    fun providesItemDao(database: AppDatabase): ItemsDao {
        return database.dataDao()
    }
}