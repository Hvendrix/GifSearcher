package com.example.gifsearcher.core.modules

import com.example.gifsearcher.core.data.repositories.GifRepository
import com.example.gifsearcher.core.domain.usecase.MainInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorModule {

    @Provides
    @Singleton
    fun provideMainInteractor(gifRepository: GifRepository): MainInteractor =
        MainInteractor(gifRepository)
}