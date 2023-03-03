package com.example.gifsearcher.core.data.model

import com.google.gson.annotations.SerializedName

data class PostSearchGifs(
    val limit: Int,
    val rating: String,
    @SerializedName("q")
    val searchString: String,
    val offset: Int,
)

fun PostSearchGifs.toMap() =
    mapOf(
        "limit" to limit.toString(),
        "rating" to rating,
        "q" to searchString,
        "offset" to offset.toString(),
    )

