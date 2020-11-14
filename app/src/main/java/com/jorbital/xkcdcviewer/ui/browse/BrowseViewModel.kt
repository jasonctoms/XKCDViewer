package com.jorbital.xkcdcviewer.ui.browse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorbital.service.data.ComicDto
import com.jorbital.service.repository.XkcdRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BrowseViewModel(private val xkcdRepository: XkcdRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _selectedComic = MutableLiveData<ComicDto>()
    val selectedComic: LiveData<ComicDto> = _selectedComic

    fun getLatestComic() {
        xkcdRepository.getCurrentComic().onEach { _selectedComic.postValue(it) }.launchIn(viewModelScope)
    }
}