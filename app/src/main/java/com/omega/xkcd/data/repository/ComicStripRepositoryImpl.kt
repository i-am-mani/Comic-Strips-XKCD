package com.omega.xkcd.data.repository

import com.omega.xkcd.data.room.ComicStripDao
import com.omega.xkcd.data.retrofit.XKCDComicStripRetrofitService
import com.omega.xkcd.domain.models.ComicStripDomainModel
import com.omega.xkcd.domain.repository.ComicStripsRepository

class ComicStripRepositoryImpl(private val database: ComicStripDao, private val remote: XKCDComicStripRetrofitService) :
    ComicStripsRepository {

    override suspend fun getLatestComicStrip(): ComicStripDomainModel {
        return remote.getLatestXKCDComic().toComicStripDomainModel()  // TODO: Provide Caching facility
    }

    override suspend fun getComicStrip(number: Int): ComicStripDomainModel {
        return  remote.getXKCDComic(number).toComicStripDomainModel()
    }

    override suspend fun getAllFavoriteComicStrips(): List<ComicStripDomainModel> {
        val roomModels = database.getComicStrips()
        return roomModels.map { roomModel -> roomModel.toDomainModel() }
    }

}