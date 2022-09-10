package com.gs.test

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gs.test.data.model.Item
import com.gs.test.ui.feature.ItemDetailScreen
import com.gs.test.ui.feature.utils.UiState
import com.gs.test.ui.theme.ComposeSampleTheme
import org.junit.Rule
import org.junit.Test

class ItemDetails {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val itemLiveData = MutableLiveData<UiState<Item>>(UiState.Loading)
    private fun getItemsLiveData(): LiveData<UiState<Item>> = itemLiveData

    private val itemFavLiveData = MutableLiveData<List<Item>>()
    private val item: Item = Item(
        overview = "Overview2",
        date = "2022/09/12",
        url = "",
        title = "Title2",
        media_type = "image"
    )


    @Test
    fun test_card_details() {
        composeTestRule.setContent {
            itemLiveData.postValue(UiState.Success(data = item))
            ComposeSampleTheme {
                ItemDetailScreen(livedata = getItemsLiveData(), null, itemFavLiveData)
            }
        }
        composeTestRule
            .onNode(hasText("Title2"))
        composeTestRule
            .onNode(hasText("2022/09/12"))
        composeTestRule
            .onNode(hasText("Overview2"))
    }
}