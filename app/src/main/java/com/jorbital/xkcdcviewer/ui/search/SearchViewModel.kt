package com.jorbital.xkcdcviewer.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorbital.service.data.ComicDto
import com.jorbital.service.data.RelevantXkcdResult
import com.jorbital.service.repository.RelevantXkcdRepository
import com.jorbital.service.repository.XkcdRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

class SearchViewModel(private val relevantXkcdRepository: RelevantXkcdRepository, private val xkcdRepository: XkcdRepository) : ViewModel() {

    private val _loading = MutableLiveData<Any>()
    val loading: LiveData<Any> = _loading

    private val _percentMatch = MutableLiveData<Double>()
    val percentMatch: LiveData<Double> = _percentMatch

    private val _selectedComic = MutableLiveData<ComicDto>()
    val selectedComic: LiveData<ComicDto> = _selectedComic

    fun performSearch(queryString: String) {
        relevantXkcdRepository.getRelevantComic(queryString).onStart { _loading.postValue("") }.onEach {
            Timber.d("Search result is $it")
            getSelectedComic(it)
        }.launchIn(viewModelScope)
    }

    private fun getSelectedComic(relevantXkcdResult: RelevantXkcdResult) {
        xkcdRepository.getSpecificComic(relevantXkcdResult.firstComicId).onEach {
            _percentMatch.postValue(relevantXkcdResult.percentMatch)
            _selectedComic.postValue(it)
        }.launchIn(viewModelScope)
    }
}