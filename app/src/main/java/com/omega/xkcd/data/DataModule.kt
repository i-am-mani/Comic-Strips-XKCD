package com.omega.xkcd.data

import com.omega.xkcd.data.repository.ComicStripRepositoryImpl
import com.omega.xkcd.data.retrofit.XKCDComicStripRetrofitService
import com.omega.xkcd.data.room.ComicStripsDatabase
import com.omega.xkcd.data.room.XKCDComicStripDao
import com.omega.xkcd.domain.repository.ComicStripsRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

internal val dataModule = Kodein.Module("Data"){

    bind<XKCDComicStripDao>() with singleton {
        instance<ComicStripsDatabase>().XKCDComicStripDao()
    }

    bind<XKCDComicStripRetrofitService>() with singleton {
        instance<Retrofit>().create(XKCDComicStripRetrofitService::class.java)
    }

    bind<ComicStripsRepository>() with singleton { ComicStripRepositoryImpl(instance(),instance()) }
}