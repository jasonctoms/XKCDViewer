package com.jorbital.xkcdcviewer.koin

import com.jorbital.xkcdcviewer.ui.browse.BrowseViewModel
import com.jorbital.xkcdcviewer.ui.favorites.FavoritesViewModel
import com.jorbital.xkcdcviewer.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object UiKoinModule {
    var module = module {
        viewModel { BrowseViewModel(xkcdRepository = get(), favoritesRepository = get()) }
        viewModel { SearchViewModel(relevantXkcdRepository = get(), xkcdRepository = get(), favoritesRepository = get()) }
        viewModel { FavoritesViewModel(favoritesRepository = get(), xkcdRepository = get()) }
    }
}