package com.omega.xkcd

import android.app.Application
import android.content.Context
import android.util.Log
import com.omega.xkcd.data.dataModule
import com.omega.xkcd.data.room.ComicStripsDatabase
import com.omega.xkcd.domain.domainModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

/*
False positive "Unused symbol" for a custom Android application class referenced in AndroidManifest.xml file:
https://youtrack.jetbrains.net/issue/KT-27971
*/
class ComicStripsApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
//        import(androidXModule(this@ComicStripsApplication))
        bind<ComicStripsDatabase>() with singleton {
            Log.wtf("DEBUG", "this@...  = $this@ComicStripsApplication")
            ComicStripsDatabase.getDatabase(context as Context)
        }
        import(appModule)
        import(dataModule)
//        import(domainModule)
        constant("max") with 5
        bind<String>() with singleton { String() }
    }

    override fun onCreate() {
        super.onCreate()
    }
}
