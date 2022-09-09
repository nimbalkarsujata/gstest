package com.gs.test.di.module

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {

    @Provides
    fun provideAppContext(): Context {
        return context.applicationContext
    }
}