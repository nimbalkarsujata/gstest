package com.gs.test.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gs.test.data.model.Item
import com.gs.test.data.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemDetailViewModel(savedStateHandle: SavedStateHandle, repository: DataRepository) :
    ViewModel() {
    val item = mutableStateOf<Item?>(null)

    init {
        val itemId: Int = savedStateHandle.get<Int>("item_id") ?: 0
        viewModelScope.launch(Dispatchers.IO) {
            item.value = repository.getSingleItemDetails(itemId)
        }
    }
}