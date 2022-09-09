@file:Suppress("UNCHECKED_CAST")

package com.gs.test.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gs.test.data.repository.DataRepository
import java.util.*

class DataViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    var map: HashMap<String, Any> = HashMap()
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemsDataViewModel::class.java)) {
            return ItemsDataViewModel(repository = repository) as T
        }
        if (modelClass.isAssignableFrom(ItemDetailViewModel::class.java)) {
            return ItemDetailViewModel(repository = repository, savedStateHandle = SavedStateHandle(map)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}