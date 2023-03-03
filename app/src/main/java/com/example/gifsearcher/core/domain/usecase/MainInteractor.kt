package com.example.gifsearcher.core.domain.usecase

import com.example.gifsearcher.core.data.model.Gif
import com.example.gifsearcher.core.data.model.PostGifs
import com.example.gifsearcher.core.data.model.PostSearchGifs
import com.example.gifsearcher.core.data.repositories.GifRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class MainInteractor(private val gifRepository: GifRepository) {
    suspend fun getGifs(postGifs: PostGifs): ArrayList<Gif?>? =
        try {
            withContext(Dispatchers.IO) {
                return@withContext gifRepository.getGifs(postGifs).execute().body()?.gifArray ?: error(
                    "error get gifs"
                )
            }
        } catch (e: Exception) {
            Timber.e(e)
            null
        }


    suspend fun getGifById(id: String = "l0EwYIdgjxrHOqSkw") =
        try {
            withContext(Dispatchers.IO) {
                return@withContext gifRepository.getGifById(id).execute().body()?.gif ?: error(
                    "error get gifs"
                )
            }
        } catch (e: Exception) {
            Timber.e(e)
            null
        }

    suspend fun searchGifs(postSearchGifs: PostSearchGifs) =
        try {
            withContext(Dispatchers.IO) {
                return@withContext gifRepository.searchGifs(postSearchGifs).execute().body()?.gifArray ?: error(
                    "error get gifs"
                )
            }
        } catch (e: java.lang.Exception){
            Timber.e(e)
            null
        }
}