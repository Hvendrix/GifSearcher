package com.example.gifsearcher.core.data.api

import com.example.gifsearcher.core.data.model.PostGifs
import com.example.gifsearcher.core.data.model.PostSearchGifs
import com.example.gifsearcher.core.data.model.toMap
import com.example.gifsearcher.core.domain.converters.GifJSONFactory
import retrofit2.Call

class GifApiHelperImpl(private val gifApiService: GifApiService): GifApiHelper {

    override suspend fun getGifs(postGifs: PostGifs): Call<GifJSONFactory> =
        gifApiService.getGifs(postGifs.toMap())

    override suspend fun searchGifs(postSearchGifs: PostSearchGifs): Call<GifJSONFactory> =
        gifApiService.searchGifs(postSearchGifs.toMap())

    override suspend fun getGifById(id: String): Call<GifJSONFactory> =
        gifApiService.getGifById(id)
}