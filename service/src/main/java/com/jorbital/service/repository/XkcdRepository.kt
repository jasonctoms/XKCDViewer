package com.jorbital.service.repository

import com.jorbital.service.api.XkcdApi
import com.jorbital.service.data.ComicDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class XkcdRepository(private val api: XkcdApi) {
    fun getCurrentComic(): Flow<ComicDto> = flow { emit(api.getLatestComic()) }.flowOn(Dispatchers.IO)
    fun getSpecificComic(number: Int): Flow<ComicDto> = flow { emit(api.getSpecificComic(number)) }.flowOn(Dispatchers.IO)
}