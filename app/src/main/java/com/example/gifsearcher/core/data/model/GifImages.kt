package com.example.gifsearcher.core.data.model

import com.google.gson.annotations.SerializedName

data class GifImages(
    @SerializedName("preview_gif")
    val gifPreview: GifPreview,

    val original: GifOriginal


)
