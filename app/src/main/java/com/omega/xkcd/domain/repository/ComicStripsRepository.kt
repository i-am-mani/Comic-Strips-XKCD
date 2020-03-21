package com.omega.xkcd.domain.repository

import com.omega.xkcd.domain.models.ComicStripDomainModel


interface ComicStripsRepository {
    suspend fun getLatestComicStrip(): ComicStripDomainModel
    suspend fun getComicStrip(number: Int): ComicStripDomainModel
    suspend fun getAllFavoriteComicStrips(): List<ComicStripDomainModel>
}