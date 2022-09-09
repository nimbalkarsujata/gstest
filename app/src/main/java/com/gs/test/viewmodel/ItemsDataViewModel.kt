package com.gs.test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gs.test.data.model.Item
import com.gs.test.data.model.Items
import com.gs.test.data.repository.DataRepository
import com.gs.test.ui.feature.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ItemsDataViewModel @Inject constructor(val repository: DataRepository) : ViewModel() {
    private val itemLiveData = MutableLiveData<UiState<Items>>(UiState.Loading)
    fun getItemsLiveData(): LiveData<UiState<Items>> = itemLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getItemsData().let {
                // Success State
                if (it.results.isNullOrEmpty().not()) {
                    itemLiveData.postValue(UiState.Success(data = it))
                } else {
                    itemLiveData.postValue(UiState.Error())
                }
            }
        }
    }

    private suspend fun getItemsData(): Items {
        return repository.getData()
    }
    private suspend fun getListData(): List<Item> {
        return repository.getFromRemoteData()
    }
}