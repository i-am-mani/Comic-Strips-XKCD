package com.omega.xkcd.data.repository

import android.util.Log
import com.omega.xkcd.data.room.ComicStripDao
import com.omega.xkcd.data.retrofit.XKCDComicStripRetrofitService
import com.omega.xkcd.data.room.ComicStripRoomModel
import com.omega.xkcd.domain.models.ComicStripDomainModel
import com.omega.xkcd.domain.repository.ComicStripsRepository

class ComicStripRepositoryImpl(private val database: ComicStripDao, private val remote: XKCDComicStripRetrofitService) :
    ComicStripsRepository {
    override suspend fun removeComicStripFromFavorite(comicStrip: ComicStripDomainModel): Boolean {
        val roomModel = ComicStripRoomModel.fromDomainModel(comicStrip)
        val removeCount = database.removeComicStrip(roomModel.number)
        Log.d("REPO", "removeCount = $removeCount")
        return removeCount > 0
    }

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
        val domainModel = remote.getLatestXKCDComic().toComicStripDomainModel()
        return markDomainModelFavorite(domainModel)
    }

    override suspend fun getComicStrip(number: Int): ComicStripDomainModel {
        val domainModel = remote.getXKCDComic(number).toComicStripDomainModel()
        return markDomainModelFavorite(domainModel)
    }

    private suspend fun markDomainModelFavorite(comicStrip:ComicStripDomainModel): ComicStripDomainModel{
        val localCopy = database.getComicStripByNumber(comicStrip.number)
        return if(localCopy != null){
            comicStrip.isFavorite = true
            comicStrip
        } else {
            comicStrip
        }
    }
}