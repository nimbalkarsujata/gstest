package com.gs.test.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "item")
data class Item(

    @SerializedName("explanation")
    val overview: String,

    @PrimaryKey
    @SerializedName("date")
    val date: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("media_type")
    val media_type: String,

    @SerializedName("title")
    val title: String
)
