package com.omega.xkcd

import android.app.Application
import com.omega.xkcd.data.dataModule
import com.omega.xkcd.data.room.ComicStripsDatabase
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import org.kodein.di.generic.with

class ComicStripsApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        //        import(androidXModule(this@ComicStripsApplication))
        bind<ComicStripsDatabase>() with singleton {
            ComicStripsDatabase.getDatabase(this@ComicStripsApplication)
        }
        import(appModule)
        import(dataModule)
        constant("max") with 5
        bind<String>() with singleton { String() }
    }

    override fun onCreate() {
        super.onCreate()
    }
}
