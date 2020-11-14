package com.jorbital.xkcdcviewer.ui.browse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorbital.service.data.ComicDto
import com.jorbital.service.repository.XkcdRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class BrowseViewModel(private val xkcdRepository: XkcdRepository) : ViewModel() {

    private val _loading = MutableLiveData<Any>()
    val loading: LiveData<Any> = _loading

    private val _selectedComic = MutableLiveData<ComicDto>()
    val selectedComic: LiveData<ComicDto> = _selectedComic

    fun getLatestComic() {
        xkcdRepository.getCurrentComic().onStart { _loading.postValue(null) }.onEach { _selectedComic.postValue(it) }.launchIn(viewModelScope)
    }
}