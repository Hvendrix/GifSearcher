package com.example.gifsearcher.fragments.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gifsearcher.core.data.model.Gif
import com.example.gifsearcher.core.data.model.PostGifs
import com.example.gifsearcher.core.data.model.PostSearchGifs
import com.example.gifsearcher.core.domain.usecase.MainInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {
    @Inject
    lateinit var mainInteractor: MainInteractor

    private val _gifs = MutableStateFlow<ArrayList<Gif?>?>(arrayListOf())
    var gifs: SharedFlow<ArrayList<Gif?>?> = _gifs.asSharedFlow()
    fun getGifs(){
        viewModelScope.launch {
            val response = async {  mainInteractor.getGifs(PostGifs(25, "g", _gifs.value?.size ?: 0))}.await()
            val oldArray= _gifs.value?.toMutableList()
            oldArray?.addAll(response?.toMutableList() ?: mutableListOf())
            _gifs.emit(oldArray?.let { ArrayList(it) })
        }
    }

    fun clearGifs(){
        _gifs.value = arrayListOf()
    }
    fun searchGifs(searchString: String){
        viewModelScope.launch {
            val response = async {  mainInteractor.searchGifs(PostSearchGifs(25, "g", searchString, _gifs.value?.size ?: 0))}.await()
            val oldArray= _gifs.value?.toMutableList()
            oldArray?.addAll(response?.toMutableList() ?: mutableListOf())
            _gifs.emit(oldArray?.let { ArrayList(it) })
        }
    }


}

