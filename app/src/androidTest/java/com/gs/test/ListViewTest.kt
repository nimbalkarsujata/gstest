package com.gs.test

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.gs.test.data.model.Item
import com.gs.test.ui.feature.DataItem
import com.gs.test.ui.feature.ListView
import com.gs.test.ui.theme.ComposeSampleTheme
import org.junit.Rule
import org.junit.Test


class ListViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_single_card_test() {
        // Start the app

        composeTestRule.setContent {
            ComposeSampleTheme {
                val navController = rememberNavController()
                DataItem(
                    Item(
                        overview = "Overview",
                        date = "2022/09/10",
                        url = "",
                        title = "Title",
                        media_type = "image"
                    ), {
                        navController.navigate("")
                    }, null
                )
            }
        }

        composeTestRule
            .onNode(hasText("Title"))
        composeTestRule
            .onNode(hasText("2022/09/10"))
        composeTestRule
            .onNode(hasText("Overview"))
    }

    private val items = listOf(
        Item(
            overview = "Overview",
            date = "2022/09/09",
            url = "",
            title = "Title",
            media_type = "image"
        ), Item(
            overview = "Overview1",
            date = "2022/09/11",
            url = "",
            title = "Title1",
            media_type = "image"
        ), Item(
            overview = "Overview2",
            date = "2022/09/12",
            url = "",
            title = "Title2",
            media_type = "image"
        )
    )

    @Test
    fun test_list_test() {
        composeTestRule.setContent {
            ComposeSampleTheme {
                val navController = rememberNavController()
                ListView(itemList = items, itemViewModel = null, navCallBack = {})
            }
        }

        composeTestRule
            .onNode(hasText("Title"))
        composeTestRule
            .onNode(hasText("2022/09/09"))
        composeTestRule
            .onNode(hasText("Overview"))

        composeTestRule.onNodeWithText("Title").performClick()
    }
}