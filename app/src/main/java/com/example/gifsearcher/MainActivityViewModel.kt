package com.example.gifsearcher

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(): ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _reloadGifs = MutableStateFlow(false)
    val reloadGifs: StateFlow<Boolean> = _reloadGifs.asStateFlow()

    var searchPressed: Boolean = false
    fun updateSearchQuery(searchQuery: String? = ""){
        _searchQuery.value = searchQuery ?: ""
    }

    fun needReloadGifs(need: Boolean){
        _reloadGifs.value = need
    }
}