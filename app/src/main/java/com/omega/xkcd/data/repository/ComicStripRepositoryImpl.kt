package com.omega.xkcd.data.repository

import com.omega.xkcd.data.room.ComicStripDao
import com.omega.xkcd.data.retrofit.XKCDComicStripRetrofitService
import com.omega.xkcd.data.room.ComicStripRoomModel
import com.omega.xkcd.domain.models.ComicStripDomainModel
import com.omega.xkcd.domain.repository.ComicStripsRepository

class ComicStripRepositoryImpl(private val database: ComicStripDao, private val remote: XKCDComicStripRetrofitService) :
    ComicStripsRepository {

    private lateinit var mFavoriteComicStrips: MutableList<ComicStripDomainModel>
    private var mIdx = 0

    override suspend fun getAllFavoriteComicStrips(): List<ComicStripDomainModel> {
        if(!::mFavoriteComicStrips.isInitialized){
            initializeFavoriteComicStrips()
        }
        return mFavoriteComicStrips
    }

    override suspend fun removeComicStripFromFavorite(comicStrip: ComicStripDomainModel): Boolean {
        val roomModel = ComicStripRoomModel.fromDomainModel(comicStrip)
        val status = database.removeComicStrip(roomModel.number) > 0
        if(status)mFavoriteComicStrips.removeIf { it.number == roomModel.number }
        return status
    }

    override suspend fun getNextFavoriteComicStrip(): ComicStripDomainModel {
        val count = mFavoriteComicStrips.count()
        // wrap around if out of index
        mIdx = (mIdx + 1) % count
        return mFavoriteComicStrips[mIdx]
    }

    override suspend fun getPreviousFavoriteComicStrip(): ComicStripDomainModel {
        mIdx = (mIdx - 1) % 1 // So that if -1 then idx is 0
        return mFavoriteComicStrips[mIdx]
    }

    override suspend fun getLatestFavoriteComicStrip(): ComicStripDomainModel {
        initializeFavoriteComicStrips()
        val latestComicStrip = mFavoriteComicStrips[0]
        mIdx = 0
        return latestComicStrip
    }

    private suspend fun initializeFavoriteComicStrips() {
        if (!::mFavoriteComicStrips.isInitialized) {
            mFavoriteComicStrips =
                database.getAllComicStrips().map { it.toDomainModel() }.toMutableList()
        }
    }

    override suspend fun addComicStripToFavorites(comicStrip: ComicStripDomainModel): Boolean {
        val model = ComicStripRoomModel.fromDomainModel(comicStrip)
        val response = database.addComicStrip(model) > 0
        if(response) mFavoriteComicStrips.add(comicStrip) // Append the comic
        return response
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