package com.jorbital.xkcdcviewer.koin

import com.jorbital.xkcdcviewer.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object SearchKoinModule {
    var module = module {
        viewModel { SearchViewModel(relevantXkcdRepository = get(), xkcdRepository = get()) }
    }
}