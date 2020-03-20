package com.omega.xkcd.data.retrofit

import com.omega.xkcd.domain.models.XKCDComicStripModel
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface XKCDComicStripRetrofitService{

    @GET("{comicNumber}/info.0.json")
    suspend fun getXKCDComic(@Path("comicNumber") number: Int): XKCDComicStripModel

    @GET("info.0.json")
    suspend fun getLatestXKCDComic() : XKCDComicStripModel
}