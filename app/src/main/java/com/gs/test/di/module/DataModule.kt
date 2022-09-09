package com.gs.test.di.module

import androidx.lifecycle.ViewModel
import com.gs.test.data.repository.DataRepository
import com.gs.test.viewmodel.DataViewModelFactory
import dagger.MapKey
import dagger.Module
import dagger.Provides
import kotlin.reflect.KClass

@Module
class DataModule {

    @Provides
    fun provideViewModelFactory(repository: DataRepository): DataViewModelFactory {
        return DataViewModelFactory(repository = repository)
    }
}

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)