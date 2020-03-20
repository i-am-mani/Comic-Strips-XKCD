package com.omega.xkcd.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class XKCDComicStripModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int, // Make it PK
    val month: Int,
    val year: Int,
    val day: Int,
    val num: Int,
    val title: String,
    val transcript: String,
    val alt: String,
    @field:Json(name="img")
    val path: String,
    val isFavorite: Boolean = false
)