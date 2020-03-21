package com.omega.xkcd.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.omega.xkcd.domain.models.ComicStripDomainModel
import java.util.*

@Entity
data class ComicStripRoomModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val number: Int, // Comic number for the provider
    val date: Long,
    val title: String,
    val transcript: String,
    val alt: String,
    val imgLocalPath: String? = null,
    val imgRemotePath: String,
    val provider: String // like XKCD,Dilbert etc
) {
    fun toDomainModel(): ComicStripDomainModel {
        val d = Date(date)
        return ComicStripDomainModel(
            number = number,
            date = d,
            title = title,
            transcript = transcript,
            alt = alt,
            imageLocalPath = imgLocalPath,
            imgRemotePath = imgRemotePath,
            provider = provider
            )
    }
}