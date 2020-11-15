package com.jorbital.service.repository

import com.jorbital.service.api.RelevantXkcdApi
import com.jorbital.service.data.RelevantXkcdResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RelevantXkcdRepository(private val relevantXkcdApi: RelevantXkcdApi) {
    fun getRelevantComic(queryString: String): Flow<RelevantXkcdResult> = flow {
        val result = relevantXkcdApi.searchForRelevantComic(queryString)
        emit(parseResult(result))
    }.flowOn(Dispatchers.IO)

    private fun parseResult(result: String): RelevantXkcdResult {
        val percentMatch = (result.substringBefore("\n").toDoubleOrNull() ?: 0.0) * 100
        val resultWithoutPercent = result.substringAfter("0 \n")
        val firstComicId = resultWithoutPercent.substringBefore(" ").toIntOrNull() ?: 0

        return RelevantXkcdResult(percentMatch, firstComicId)
    }
}