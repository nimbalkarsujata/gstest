package com.gs.test.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gs.test.data.model.Item
import com.gs.test.data.model.Items
import com.gs.test.data.repository.DataRepository
import com.gs.test.ui.feature.utils.UiState
import com.gs.test.ui.feature.utils.getDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ItemsDataViewModel @Inject constructor(val repository: DataRepository) : ViewModel() {

    val itemFavLiveData = MutableLiveData<List<Item>>()
    fun getFavItemsLiveData(): LiveData<List<Item>> = itemFavLiveData

    private val itemLiveData = MutableLiveData<UiState<Items>>(UiState.Loading)
    fun getItemsLiveData(): LiveData<UiState<Items>> = itemLiveData
    private val isListRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean>
        get() = isListRefreshing.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            postData(fetchItemsData())
            val likedItem = repository.getFavouriteItems()
            itemFavLiveData.postValue(likedItem)
        }
    }

    fun refreshData() {
        fetchBasedOnDateSelection(getDate(latest = false))
    }
    fun updateValue(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            postData(repository.updateItem(item, itemFavLiveData.value?.contains(item) ?: false))
            val likedItem = repository.getFavouriteItems()
            itemFavLiveData.postValue(likedItem)
        }
    }

    fun fetchBasedOnDateSelection(date: String) {
        Log.d("Sujata", "Selected date : $date")
        viewModelScope.launch(Dispatchers.IO) {
            itemLiveData.postValue(UiState.Loading)
            postData(repository.getFromRemoteData(date = date))
        }
    }

    private fun postData(items: Items) {
        try {
            items.let {
                // Success State
                if (it.results.isNullOrEmpty().not()) {
                    itemLiveData.postValue(UiState.Success(data = it))
                } else {
                    itemLiveData.postValue(UiState.Error())
                }
            }
        } catch (e: Exception) {
            itemLiveData.postValue(UiState.Error())
        }
    }

    private suspend fun fetchItemsData(): Items {
        return repository.getData()
    }

}