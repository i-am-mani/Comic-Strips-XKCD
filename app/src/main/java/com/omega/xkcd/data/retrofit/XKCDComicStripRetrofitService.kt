package com.omega.xkcd.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface XKCDComicStripRetrofitService{

    @GET("{comicNumber}/info.0.json")
    suspend fun getXKCDComic(@Path("comicNumber") number: Int): XKCDComicStripModel

    @GET("info.0.json")
    suspend fun getLatestXKCDComic() : XKCDComicStripModel
}