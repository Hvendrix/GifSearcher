package com.example.gifsearcher.core.data.repositories

import com.example.gifsearcher.core.data.api.GifApiHelper
import com.example.gifsearcher.core.data.model.PostGifs
import com.example.gifsearcher.core.data.model.PostSearchGifs
import com.example.gifsearcher.core.domain.converters.GifJSONFactory
import retrofit2.Call
class GifRepositoryImpl(private val gifApiHelper: GifApiHelper): GifRepository {
    override suspend fun getGifs(postGifs: PostGifs): Call<GifJSONFactory> =
        gifApiHelper.getGifs(postGifs)
    override suspend fun searchGifs(postSearchGifs: PostSearchGifs): Call<GifJSONFactory> =
        gifApiHelper.searchGifs(postSearchGifs)
    override suspend fun getGifById(id: String): Call<GifJSONFactory> =
        gifApiHelper.getGifById(id)
}