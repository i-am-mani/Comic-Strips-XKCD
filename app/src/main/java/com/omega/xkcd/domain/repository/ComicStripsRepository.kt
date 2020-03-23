package com.omega.xkcd.domain.repository

import com.omega.xkcd.domain.models.ComicStripDomainModel


interface ComicStripsRepository {
    suspend fun getLatestComicStrip(): ComicStripDomainModel
    suspend fun getComicStrip(number: Int): ComicStripDomainModel
    suspend fun addComicStripToFavorites(comicStrip: ComicStripDomainModel): Boolean
    suspend fun getLatestFavoriteComicStrip(): ComicStripDomainModel
    suspend fun getNextFavoriteComicStrip(): ComicStripDomainModel
    suspend fun getPreviousFavoriteComicStrip(): ComicStripDomainModel
    suspend fun removeComicStripFromFavorite(comicStrip: ComicStripDomainModel): Boolean
    suspend fun getAllFavoriteComicStrips(): List<ComicStripDomainModel>
}