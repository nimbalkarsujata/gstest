package com.gs.test.di.component

import com.gs.test.di.module.*
import com.gs.test.ui.feature.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, DataBaseModule::class, DataSourceModule::class, RemoteModule::class, RepositoryModule::class, DataModule::class]
)
interface AppComponent {
    fun inject(activity: MainActivity)
}