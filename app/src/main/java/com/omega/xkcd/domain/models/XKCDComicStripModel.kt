package com.omega.xkcd.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class XKCDComicStripModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int, // Make it PK
    val month: Int,
    val year: Int,
    val title: String,
    val transcript: String,
    val alt: String,
    val path: String,
    val day: String,
    val isFavorite: Boolean = false
)