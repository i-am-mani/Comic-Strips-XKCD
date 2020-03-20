package com.omega.xkcd.presentation.feature_home

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omega.xkcd.domain.models.XKCDComicStripModel
import com.omega.xkcd.domain.repository.ComicStripsRepository
import kotlinx.coroutines.launch

class HomeViewModel( val repository: ComicStripsRepository) :
    ViewModel() {

    val mComicStrip = MutableLiveData<XKCDComicStripModel>()
    private  var MAX_COMIC_NUMBER = 0
    val isLoading = MutableLiveData<Boolean>(true)

    init {
        viewModelScope.launch {
            val latestComicStrip = repository.getLatestComicStrip()
            mComicStrip.value = latestComicStrip
            MAX_COMIC_NUMBER = latestComicStrip.num
            isLoading.value = false
        }
    }


    private fun fetchComicWithNumber(number: Int){
        isLoading.value = true
        viewModelScope.launch {
            val comicStrip = repository.getComicStrip(number)
            mComicStrip.value = comicStrip
            isLoading.value = false
        }
    }

    fun nextComicStrip(){
        val comicNumber = getComicStripNumber()
        if(comicNumber != null && (comicNumber + 1 < MAX_COMIC_NUMBER)){
            fetchComicWithNumber(comicNumber + 1)
        }
    }

    fun previousComicStrip(){
        val comicNumber = getComicStripNumber()
        if(comicNumber != null && (comicNumber - 1 > 0)){
            fetchComicWithNumber(comicNumber -1 )
        }
    }

    fun getComicStripNumber(): Int?{
        return mComicStrip.value?.num
    }

}