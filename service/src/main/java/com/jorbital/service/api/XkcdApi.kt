package com.jorbital.service.api

import com.jorbital.service.data.ComicDto
import retrofit2.http.GET
import retrofit2.http.Path

interface XkcdApi {
    @GET("info.0.json")
    suspend fun getLatestComic(): ComicDto

    @GET("{comicNumber}/info.0.json")
    suspend fun getSpecificComic(@Path("comicNumber") comicNumber: Int): ComicDto
}