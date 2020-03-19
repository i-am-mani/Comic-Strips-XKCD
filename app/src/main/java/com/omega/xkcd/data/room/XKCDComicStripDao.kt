package com.omega.xkcd.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.omega.xkcd.domain.models.XKCDComicStripModel

@Dao
interface XKCDComicStripDao {

    @Insert
    suspend fun addComicStrip(comicStrip: XKCDComicStripModel) : Long

    @Query("SELECT * FROM XKCDComicStripModel WHERE isFavorite = 1")
    suspend fun getFavoriteComicStrips() : List<XKCDComicStripModel>
}