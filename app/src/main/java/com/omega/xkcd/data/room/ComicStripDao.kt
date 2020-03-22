package com.omega.xkcd.data.room

import androidx.room.*
import com.omega.xkcd.domain.models.ComicStripDomainModel

@Dao
interface ComicStripDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun addComicStrip(comicStripDomain: ComicStripRoomModel) : Long

    @Query("SELECT * FROM ComicStripRoomModel")
    suspend fun getComicStrips() : List<ComicStripRoomModel>
}