package com.jorbital.service.koin

import com.jorbital.service.api.RelevantXkcdApi
import com.jorbital.service.repository.RelevantXkcdRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object RelevantXkcdKoinModule {
    var module = module {
        single<RelevantXkcdApi> {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://relevantxkcd.appspot.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
            retrofit.create(RelevantXkcdApi::class.java)
        }

        factory { RelevantXkcdRepository(relevantXkcdApi = get()) }
    }
}