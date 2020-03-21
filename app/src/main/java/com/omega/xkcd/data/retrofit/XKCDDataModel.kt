package com.omega.xkcd.data.retrofit

import androidx.core.util.TimeUtils
import com.omega.xkcd.data.room.ComicStripRoomModel
import com.omega.xkcd.domain.enum.ComicStripProviders
import com.omega.xkcd.domain.models.ComicStripDomainModel
import java.util.*


data class XKCDComicStripModel(
    val num: Int,
    val month: Int,
    val year: Int,
    val day: Int,
    val title: String,
    val transcript: String,
    val alt: String,
    val img: String
) {
    fun toComicStripRoomModel(): ComicStripRoomModel {
        val date = Date(year, month, day).time
        return ComicStripRoomModel(
            number = num,
            date = date,
            title = title,
            transcript = transcript,
            alt = alt,
            imgRemotePath = img,
            provider = ComicStripProviders.XKCD.name
        )
    }

    fun toComicStripDomainModel(): ComicStripDomainModel {
        val date = Date(year - 1900, month - 1, day)
        return ComicStripDomainModel(
            number = num,
            date = date,
            title = title,
            transcript = transcript,
            alt = alt,
            imgRemotePath = img,
            provider = ComicStripProviders.XKCD.name
        )
    }
}