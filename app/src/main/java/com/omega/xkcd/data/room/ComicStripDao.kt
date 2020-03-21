package com.omega.xkcd.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.omega.xkcd.domain.models.ComicStripDomainModel

@Dao
interface ComicStripDao {

    @Insert
    suspend fun addComicStrip(comicStripDomain: ComicStripRoomModel) : Long

    @Query("SELECT * FROM ComicStripRoomModel")
    suspend fun getComicStrips() : List<ComicStripRoomModel>
}