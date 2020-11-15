package com.jorbital.xkcdcviewer.ui.browse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorbital.service.data.ComicDto
import com.jorbital.service.repository.FavoritesRepository
import com.jorbital.service.repository.XkcdRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class BrowseViewModel(private val xkcdRepository: XkcdRepository, private val favoritesRepository: FavoritesRepository) : ViewModel() {

    private val _loading = MutableLiveData<Any>()
    val loading: LiveData<Any> = _loading

    private val _selectedComic = MutableLiveData<ComicDto>()
    val selectedComic: LiveData<ComicDto> = _selectedComic

    var latestComicNumber = 0
    var currentComicNumber = 0

    fun getLatestComic() {
        xkcdRepository.getCurrentComic().onStart { _loading.postValue("") }.onEach { comic ->
            latestComicNumber = comic.comicNumber
            currentComicNumber = comic.comicNumber
            favoritesRepository.comicIsFavorite(currentComicNumber).collect {
                _selectedComic.postValue(comic.copy(favorite = it))
            }
        }.launchIn(viewModelScope)
    }

    fun getNextComic() {
        getSpecificComic(currentComicNumber + 1)
    }

    fun getPreviousComic() {
        getSpecificComic(currentComicNumber - 1)
    }

    fun getSpecificComic(comicNumber: Int) {
        xkcdRepository.getSpecificComic(comicNumber).onStart { _loading.postValue("") }.onEach { comic ->
            currentComicNumber = comic.comicNumber
            favoritesRepository.comicIsFavorite(currentComicNumber).collect {
                _selectedComic.postValue(comic.copy(favorite = it))
            }
        }.launchIn(viewModelScope)
    }

    fun toggleFavorite(number: Int) {
        viewModelScope.launch {
            favoritesRepository.toggleFavorite(number)
        }
    }
}