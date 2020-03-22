package com.omega.xkcd.data.repository

import com.omega.xkcd.data.room.ComicStripDao
import com.omega.xkcd.data.retrofit.XKCDComicStripRetrofitService
import com.omega.xkcd.data.room.ComicStripRoomModel
import com.omega.xkcd.domain.models.ComicStripDomainModel
import com.omega.xkcd.domain.repository.ComicStripsRepository

class ComicStripRepositoryImpl(private val database: ComicStripDao, private val remote: XKCDComicStripRetrofitService) :
    ComicStripsRepository {
    private var currentComicStripId:Int = -1

    override suspend fun getNextFavoriteComicStrip(): ComicStripDomainModel {
        return if(currentComicStripId != -1){
            val idToFetch = currentComicStripId + 1
            val comicStrip = database.getComicStrip(idToFetch).toDomainModel()
            currentComicStripId = idToFetch
            comicStrip
        } else{
            getLatestComicStrip()
        }
    }

    override suspend fun getPreviousFavoriteComicStrip(): ComicStripDomainModel {
        return if(currentComicStripId != -1){
            val idToFetch = currentComicStripId - 1
            val comicStripRoomModel = database.getComicStrip(idToFetch)
            val comicStrip = comicStripRoomModel.toDomainModel()
            currentComicStripId = idToFetch
            comicStrip
        } else{
            getLatestComicStrip()
        }
    }

    override suspend fun getLatestFavoriteComicStrip(): ComicStripDomainModel {
        val latestComicStrip = database.getLatestComicStrip()
        currentComicStripId = latestComicStrip.id
        return latestComicStrip.toDomainModel()
    }

    override suspend fun addComicStripToFavorites(comicStrip: ComicStripDomainModel): Boolean {
        val model = ComicStripRoomModel.fromDomainModel(comicStrip)
        val response = database.addComicStrip(model)
        return response > 0
    }

    override suspend fun getLatestComicStrip(): ComicStripDomainModel {
        return remote.getLatestXKCDComic().toComicStripDomainModel()
    }

    override suspend fun getComicStrip(number: Int): ComicStripDomainModel {
        return  remote.getXKCDComic(number).toComicStripDomainModel()
    }

}