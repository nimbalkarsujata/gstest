package com.gs.test.repository

import com.gs.test.data.model.Item
import com.gs.test.data.model.Items
import com.gs.test.data.repository.DataRepository

class MockDataRepository : DataRepository{
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

    private val singleItem = Item(
        overview = "Overview",
        date = "2022/09/09",
        url = "",
        title = "Title",
        media_type = "image"
    )

    override suspend fun getData(): Items {
        return Items(items)
    }

    override suspend fun getFromRemoteData(date: String): Items {
        return Items(items)
    }

    override suspend fun getDataByDate(date: String): Item {
        return singleItem
    }

    override suspend fun updateItem(data: Item, isExist: Boolean): Items {
        return Items(items)
    }

    override suspend fun getFavouriteItems(): List<Item> {
        return items
    }

    override suspend fun getFavouriteItemByDate(date: String): Item? {
        return singleItem
    }
}