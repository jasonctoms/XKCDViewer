package com.jorbital.xkcdcviewer.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorbital.service.data.ComicDto
import com.jorbital.service.repository.FavoritesRepository
import com.jorbital.service.repository.XkcdRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FavoritesViewModel(private val favoritesRepository: FavoritesRepository, private val xkcdRepository: XkcdRepository) : ViewModel() {

    private val _favorites: MutableLiveData<List<ComicDto>> = MutableLiveData()
    val favorites: LiveData<List<ComicDto>> = _favorites

    private val _emptyFavorites: MutableLiveData<Any> = MutableLiveData()
    val emptyFavorites: LiveData<Any> = _emptyFavorites

    init {
        favoritesRepository.getFavorites().onEach {
            if (it == null) {
                _emptyFavorites.postValue("")
            } else {
                getAllComics(it.toList())
            }
        }.launchIn(viewModelScope)
    }

    private fun getAllComics(ids: List<String>) {
        viewModelScope.launch {
            val favoriteComics = ids.map {
                val comicNumber = it.toInt()
                xkcdRepository.getSpecificComic(comicNumber).first()
            }
            _favorites.postValue(favoriteComics)
        }
    }
}