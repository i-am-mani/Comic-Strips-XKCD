package com.omega.xkcd.data.room

import androidx.room.*
import com.omega.xkcd.domain.models.ComicStripDomainModel

@Dao
interface ComicStripDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun addComicStrip(comicStripDomain: ComicStripRoomModel) : Long

    @Query("SELECT * from ComicStripRoomModel WHERE id = (SELECT max(id) from ComicStripRoomModel)")
    suspend fun getLatestComicStrip(): ComicStripRoomModel

    @Query("SELECT * from ComicStripRoomModel WHERE id = :id")
    suspend fun getComicStrip(id: Int): ComicStripRoomModel

    @Query("SELECT * from ComicStripRoomModel WHERE number=:number")
    suspend fun getComicStripByNumber(number:Int): ComicStripRoomModel?

    @Query("DELETE FROM ComicStripRoomModel WHERE number=:number")
    suspend fun removeComicStrip(number: Int): Int
}