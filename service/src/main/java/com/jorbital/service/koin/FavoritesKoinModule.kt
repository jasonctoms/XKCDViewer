package com.jorbital.service.koin

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.jorbital.service.data.FavoritesStorage
import com.jorbital.service.repository.FavoritesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object FavoritesKoinModule {
    private val Context.dataStore by preferencesDataStore(name = "favorites_store")
    var module = module {
        single { FavoritesStorage(androidContext().dataStore) }
        factory { FavoritesRepository(favoritesStorage = get()) }
    }
}