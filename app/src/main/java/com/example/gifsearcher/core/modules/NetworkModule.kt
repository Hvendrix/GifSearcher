package com.example.gifsearcher.core.modules

import com.example.gifsearcher.BuildConfig
import com.example.gifsearcher.core.data.api.GifApiHelper
import com.example.gifsearcher.core.data.api.GifApiHelperImpl
import com.example.gifsearcher.core.data.api.GifApiService
import com.example.gifsearcher.core.data.repositories.GifRepository
import com.example.gifsearcher.core.data.repositories.GifRepositoryImpl
import com.example.gifsearcher.core.domain.converters.GifDeserializer
import com.example.gifsearcher.core.domain.converters.GifJSONFactory
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideBaseURL() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()

                val url = original.url.newBuilder()
//                    .addQueryParameter("api_key", "0vM3Zcy4W000qmenOdjkfrMAt4x3xeyR")
                    .addQueryParameter("api_key", BuildConfig.API_KEY)
                    .build()

                val request = original.newBuilder().url(url).build()

                chain.proceed(request)
            }
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(
                GsonConverterFactory
                    .create(
                        GsonBuilder().registerTypeAdapter(
                            GifJSONFactory::class.java,
                            GifDeserializer()
                        ).create()
                    )
            )
            .addConverterFactory(GsonConverterFactory.create())


            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideGifApiService(retrofit: Retrofit): GifApiService =
        retrofit.create(GifApiService::class.java)

    @Provides
    @Singleton
    fun provideGifApiHelper(gifApiService: GifApiService): GifApiHelper =
        GifApiHelperImpl(gifApiService)

    @Provides
    @Singleton
    fun provideGifRepository(gifApiHelper: GifApiHelper): GifRepository =
        GifRepositoryImpl(gifApiHelper)

}