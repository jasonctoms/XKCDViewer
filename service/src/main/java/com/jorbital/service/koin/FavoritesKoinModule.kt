package com.jorbital.service.koin

import androidx.datastore.preferences.createDataStore
import com.jorbital.service.data.FavoritesStorage
import com.jorbital.service.repository.FavoritesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object FavoritesKoinModule {
    var module = module {
        single { FavoritesStorage(androidContext().createDataStore(name = "favorites_store")) }
        factory { FavoritesRepository(favoritesStorage = get()) }
    }
}