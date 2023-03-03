package com.example.gifsearcher.core.data.model

import com.google.gson.annotations.SerializedName

data class Gif(
    val id: String,
    val title: String,
    val images: GifImages,
    val username: String,
    val source: String,
    @SerializedName("import_datetime")
    val datetime: String,
)
