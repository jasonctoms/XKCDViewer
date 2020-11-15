package com.jorbital.service.repository

import com.jorbital.service.data.FavoritesStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take

class FavoritesRepository(private val favoritesStorage: FavoritesStorage) {
    fun getFavorites(): Flow<Set<String>?> = favoritesStorage.favoriteComics

    suspend fun comicIsFavorite(comicNumber: Int): Flow<Boolean> = flow {
        // take 1 so add/remove doesn't trigger another collection creating a loop
        getFavorites().take(1).collect {
            if (it == null) {
                emit(false)
            } else {
                emit(it.contains(comicNumber.toString()))
            }
        }
    }

    suspend fun toggleFavorite(comicNumber: Int){
        comicIsFavorite(comicNumber).collect {isFavorite ->
            if (isFavorite){
                favoritesStorage.removeFavorite(comicNumber)
            } else{
                favoritesStorage.addFavorite(comicNumber)
            }
        }
    }
}