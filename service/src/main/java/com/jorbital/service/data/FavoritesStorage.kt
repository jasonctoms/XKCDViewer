package com.jorbital.service.data

import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesSetKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesStorage(private val dataStore: DataStore<Preferences>) {

    private val favoriteComicsKey = preferencesSetKey<String>("favorite_comics_key")

    val favoriteComics: Flow<Set<String>?> = dataStore.data.map { it[favoriteComicsKey] }

    suspend fun removeFavorite(comicNumber: Int) {
        dataStore.edit { preferences ->
            preferences[favoriteComicsKey]?.let {
                val favorites = it.toMutableList()
                favorites.remove(comicNumber.toString())
                preferences[favoriteComicsKey] = favorites.toSet()
            }
        }
    }

    suspend fun addFavorite(comicNumber: Int) {
        dataStore.edit { preferences ->
            val existingFavorites = preferences[favoriteComicsKey]
            if (existingFavorites == null) {
                preferences[favoriteComicsKey] = setOf(comicNumber.toString())
            } else {
                val favorites = existingFavorites.toMutableList()
                favorites.add(comicNumber.toString())
                preferences[favoriteComicsKey] = favorites.toSet()
            }
        }
    }
}