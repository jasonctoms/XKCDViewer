package com.jorbital.service.koin

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.jorbital.service.api.XkcdApi
import com.jorbital.service.repository.XkcdRepository
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

object XkcdKoinModule {
    private val interceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @ExperimentalSerializationApi
    var module = module {
        single<XkcdApi> {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            val contentType = "application/json".toMediaType()
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://xkcd.com/")
                .addConverterFactory(Json.asConverterFactory(contentType))
                .build()
            retrofit.create(XkcdApi::class.java)
        }

        factory { XkcdRepository(api = get()) }
    }
}