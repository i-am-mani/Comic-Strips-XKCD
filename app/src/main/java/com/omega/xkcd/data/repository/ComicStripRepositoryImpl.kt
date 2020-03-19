package com.omega.xkcd.data.repository

import com.omega.xkcd.data.room.XKCDComicStripDao
import com.omega.xkcd.data.retrofit.XKCDComicStripRetrofitService
import com.omega.xkcd.domain.models.XKCDComicStripModel
import com.omega.xkcd.domain.repository.ComicStripsRepository

class ComicStripRepositoryImpl(private val database: XKCDComicStripDao, private val remote: XKCDComicStripRetrofitService) :
    ComicStripsRepository {

    override suspend fun getLatestComicStrip(): XKCDComicStripModel {
        return remote.getLatestXKCDComic()  // TODO: Provide Caching facility
    }

    override suspend fun getComicStrip(number: Int): XKCDComicStripModel {
        return  remote.getXKCDComic(number)
    }

    override suspend fun getAllFavoriteComicStrips(): List<XKCDComicStripModel> {
        return database.getFavoriteComicStrips()  // TODO: Provide Cashing facility
    }

}