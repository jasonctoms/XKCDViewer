package com.jorbital.service.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RelevantXkcdApi {
    @GET("process?action=xkcd")
    suspend fun searchForRelevantComic(@Query("query") queryString: String): String
}