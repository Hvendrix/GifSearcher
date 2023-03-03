package com.example.gifsearcher.fragments.detail.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gifsearcher.core.data.model.Gif
import com.example.gifsearcher.core.domain.usecase.MainInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var mainInteractor: MainInteractor

    private val _gif: MutableStateFlow<Gif?> = MutableStateFlow(null)
    var gif: StateFlow<Gif?> = _gif.asStateFlow()
    suspend fun getGifById(id: String) {
        viewModelScope.launch {
            launch {
                _gif.value = mainInteractor.getGifById(id)
            }
        }
    }
}