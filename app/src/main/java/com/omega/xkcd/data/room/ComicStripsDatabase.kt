package com.omega.xkcd.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.omega.xkcd.domain.models.XKCDComicStripModel

@Database(entities = arrayOf(XKCDComicStripModel::class), version = 1)
abstract class ComicStripsDatabase : RoomDatabase() {

    abstract fun XKCDComicStripDao(): XKCDComicStripDao

    companion object {
        private lateinit var INSTANCE: ComicStripsDatabase
        fun getDatabase(context: Context): ComicStripsDatabase {
            if (!::INSTANCE.isInitialized) {
                synchronized(ComicStripsDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ComicStripsDatabase::class.java, "comicStrips.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}