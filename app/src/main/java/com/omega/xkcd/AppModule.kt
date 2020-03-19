package com.omega.xkcd

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = Kodein.Module("appModule") {

    bind<Retrofit.Builder>() with singleton { Retrofit.Builder() }

    bind<Retrofit>() with singleton {
        instance<Retrofit.Builder>()
            .baseUrl(BuildConfig.baseURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}
