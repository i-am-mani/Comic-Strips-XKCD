package com.omega.xkcd.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.omega.xkcd.domain.enum.ComicStripProviders
import com.omega.xkcd.domain.models.ComicStripDomainModel
import java.util.*

@Entity
data class ComicStripRoomModel(
    val number: Int, // Comic number for the provider
    val date: Long?,
    val title: String,
    val transcript: String,
    val alt: String,
    val imgLocalPath: String? = null,
    val imgRemotePath: String,
    val provider: String // like XKCD,Dilbert etc
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun toDomainModel(): ComicStripDomainModel {
        val d = if(date != null) {
            Date(date)
        } else{
            null
        }
        return ComicStripDomainModel(
            number = number,
            date = d,
            title = title,
            transcript = transcript,
            alt = alt,
            imageLocalPath = imgLocalPath,
            imgRemotePath = imgRemotePath,
            isFavorite = true,
            provider = provider
            )
    }

    companion object{
        fun fromDomainModel(domainModel: ComicStripDomainModel): ComicStripRoomModel{
            val (number, date, title, transcript, alt, imgRemotePath, imageLocalPath, provider) = domainModel
            return ComicStripRoomModel(
                number = number,
                date = date?.time,
                title = title,
                transcript = transcript,
                alt = alt,
                imgRemotePath = imgRemotePath,  // Yet to add Local Storage facility
                provider = ComicStripProviders.XKCD.name
            )
        }
    }
}