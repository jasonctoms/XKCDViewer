package com.jorbital.service.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ComicDto(
    val title: String,
    @SerialName("safe_title") val safeTitle: String,
    @SerialName("num") val comicNumber: Int,
    @SerialName("img") val imageUrl: String,
    @SerialName("alt") val altText: String,
    val day: Int,
    val month: Int,
    val year: Int,
    val link: String,
    val news: String,
    val transcript: String,
)