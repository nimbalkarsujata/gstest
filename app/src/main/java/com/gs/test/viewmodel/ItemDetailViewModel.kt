package com.gs.test.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.gs.test.data.model.Item
import com.gs.test.data.repository.DataRepository
import com.gs.test.ui.feature.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ItemDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: DataRepository
) : ViewModel() {
    private val itemLiveData = MutableLiveData<UiState<Item>>(UiState.Loading)
    fun getItemsLiveData(): LiveData<UiState<Item>> = itemLiveData

    init {
        val itemDate: String = savedStateHandle.get<String>("date") ?: ""
        val isFavorite: Boolean = savedStateHandle.get<Boolean>("favorites") ?: false
        viewModelScope.launch(Dispatchers.IO) {
            if (!isFavorite) {
                getItemByDate(itemDate)
            } else {
                fetchDetailsFromFavorites(itemDate)
            }
        }
    }

    private suspend fun getItemByDate(date: String) {
        try {
            repository.getDataByDate(date = date)?.let {
                itemLiveData.postValue(UiState.Success(data = it))
            } ?: itemLiveData.postValue(UiState.Error())
        } catch (e: Exception) {
            itemLiveData.postValue(UiState.Error())
        }
    }

    private suspend fun fetchDetailsFromFavorites(date: String) {
        try {
            repository.getFavouriteItemByDate(date = date)?.let {
                itemLiveData.postValue(UiState.Success(data = it))
            } ?: itemLiveData.postValue(UiState.Error())
        } catch (e: Exception) {
            itemLiveData.postValue(UiState.Error())
        }
    }
}