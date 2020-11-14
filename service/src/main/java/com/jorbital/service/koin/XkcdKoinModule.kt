package com.jorbital.service.koin

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.jorbital.service.api.XkcdApi
import com.jorbital.service.repository.XkcdRepository
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit

object XkcdKoinModule {
    @ExperimentalSerializationApi
    var module = module {
        single<XkcdApi> {
            val contentType = "application/json".toMediaType()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://xkcd.com/")
                .addConverterFactory(Json.asConverterFactory(contentType))
                .build()
            retrofit.create(XkcdApi::class.java)
        }

        factory { XkcdRepository(api = get()) }
    }
}