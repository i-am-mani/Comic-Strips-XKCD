package com.omega.xkcd.domain.repository

import com.omega.xkcd.domain.models.XKCDComicStripModel

interface ComicStripsRepository {
    suspend fun getLatestComicStrip(): XKCDComicStripModel
    suspend fun getComicStrip(number: Int): XKCDComicStripModel
    suspend fun getAllFavoriteComicStrips(): List<XKCDComicStripModel>
}