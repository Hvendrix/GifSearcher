package com.example.gifsearcher.core.data.model

data class PostGifs(
    val limit: Int,
    val rating: String,
    val offset: Int,
)

fun PostGifs.toMap() =
    mapOf(
        "limit" to limit.toString(),
        "rating" to rating,
        "offset" to offset.toString(),
    )
