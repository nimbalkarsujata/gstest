package com.gs.test.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "liked_items")
data class LikedItem(
    @SerializedName("explanation")
    val overview: String? = "",

    @SerializedName("date")
    val date: String? = "",

    @SerializedName("url")
    val url: String? = "",

    @SerializedName("media_type")
    val media_type: String? = "",

    @PrimaryKey
    @SerializedName("title")
    val title: String
)


fun Item.toLikedItem() = LikedItem(
    overview = overview,
    date = date,
    url = url,
    media_type = media_type,
    title = title
)

fun LikedItem.toItem() = Item(
    overview = overview ?: "",
    date = date ?: "",
    url = url ?: "",
    media_type = media_type ?: "",
    title = title
)