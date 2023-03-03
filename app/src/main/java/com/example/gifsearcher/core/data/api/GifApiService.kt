package com.example.gifsearcher.core.data.api

import com.example.gifsearcher.core.domain.converters.GifJSONFactory
import retrofit2.Call
import retrofit2.http.*

interface GifApiService {
    @GET("gifs/trending")
    fun getGifs(@QueryMap postGifsRequest: Map<String, String>): Call<GifJSONFactory>

    @GET("gifs/search")
    fun searchGifs(@QueryMap postSearchRequest: Map<String, String>): Call<GifJSONFactory>

    @GET("gifs/{id}")
    fun getGifById(@Path("id") id: String): Call<GifJSONFactory>
}