package com.example.gifsearcher.core.domain.converters

import com.example.gifsearcher.core.data.model.Gif

data class GifJSONFactory(
    var gif : Gif? = null,
    var gifArray: ArrayList<Gif?>? = null,
)