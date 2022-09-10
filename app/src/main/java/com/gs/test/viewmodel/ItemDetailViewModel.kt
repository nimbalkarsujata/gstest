package com.gs.test.viewmodel

import androidx.lifecycle.*
import com.gs.test.data.model.Item
import com.gs.test.data.repository.DataRepository
import com.gs.test.ui.feature.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ItemDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: DataRepository
) : ViewModel() {
    private val itemLiveData = MutableLiveData<UiState<Item>>(UiState.Loading)
    fun getItemsLiveData(): LiveData<UiState<Item>> = itemLiveData

    init {
        val itemDate: String = savedStateHandle.get<String>("date") ?: ""
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getDataByDate(date = itemDate).let {
                    itemLiveData.postValue(UiState.Success(data = it))
                }
            } catch (e: Exception) {
                itemLiveData.postValue(UiState.Error())
            }
        }
    }
}