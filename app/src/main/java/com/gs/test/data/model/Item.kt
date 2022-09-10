package com.gs.test.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "item")
data class Item(
    @SerializedName("explanation")
    val overview: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("media_type")
    val media_type: String,

    @PrimaryKey()
    @SerializedName("title")
    val title: String
)