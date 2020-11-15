package com.jorbital.service.api

import retrofit2.http.GET
import retrofit2.http.Query

interface RelevantXkcdApi {
    @GET("process?action=xkcd")
    suspend fun searchForRelevantComic(@Query("query") queryString: String): String
}