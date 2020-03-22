package com.omega.xkcd.domain.models

import java.util.*

data class ComicStripDomainModel(
    val number: Int, // Comic number for the provider
    val date: Date? = null,
    val title: String,
    val transcript: String,
    val alt: String,
    val imgRemotePath: String,
    val imageLocalPath: String? = null,
    var isFavorite: Boolean = false,
    val provider: String // like XKCD,Dilbert etc
)