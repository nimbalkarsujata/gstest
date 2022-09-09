package com.gs.test.di

import com.gs.test.di.component.AppComponent

interface Injector {
    fun getDataComponent():AppComponent
}