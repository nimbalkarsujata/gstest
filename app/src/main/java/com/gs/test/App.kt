package com.gs.test

import android.app.Application
import com.gs.test.di.Injector
import com.gs.test.di.component.AppComponent
import com.gs.test.di.component.DaggerAppComponent
import com.gs.test.di.module.AppModule

class App : Application(), Injector {
    private lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()

    }

    override fun getDataComponent(): AppComponent {
        return appComponent
    }
}