package com.omega.xkcd.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ComicStripRoomModel::class), version = 3, exportSchema = false)
abstract class ComicStripsDatabase : RoomDatabase() {

    abstract fun comicStripDao(): ComicStripDao

    companion object {
        private lateinit var INSTANCE: ComicStripsDatabase
        fun getDatabase(context: Context): ComicStripsDatabase {
            if (!::INSTANCE.isInitialized) {
                synchronized(ComicStripsDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ComicStripsDatabase::class.java, "ComicStrips.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}