package com.jorbital.xkcdcviewer.koin

import com.jorbital.xkcdcviewer.ui.browse.BrowseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object BrowseKoinModule {
    var module = module {
        viewModel { BrowseViewModel(xkcdRepository = get()) }
    }
}