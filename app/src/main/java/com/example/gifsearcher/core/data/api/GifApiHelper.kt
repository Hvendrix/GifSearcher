package com.example.gifsearcher.core.data.api

import com.example.gifsearcher.core.data.model.PostGifs
import com.example.gifsearcher.core.data.model.PostSearchGifs
import com.example.gifsearcher.core.domain.converters.GifJSONFactory
import retrofit2.Call

interface GifApiHelper {
    suspend fun getGifs(postGifs: PostGifs): Call<GifJSONFactory>
    suspend fun searchGifs(postSearchGifs: PostSearchGifs): Call<GifJSONFactory>
    suspend fun getGifById(id: String): Call<GifJSONFactory>
}