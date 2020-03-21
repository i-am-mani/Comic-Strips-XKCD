package com.omega.xkcd.presentation.feature_home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omega.xkcd.domain.models.XKCDComicStripModel
import com.omega.xkcd.domain.repository.ComicStripsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class HomeViewModel(val repository: ComicStripsRepository) :
    ViewModel() {

    val TAG = "HomeViewModel"
    val mComicStrip = MutableLiveData<XKCDComicStripModel>()
    private var MAX_COMIC_NUMBER = 2222
    val isLoading = MutableLiveData<Boolean>(true)

    init {
        viewModelScope.launch {
            try {
                val latestComicStrip = repository.getLatestComicStrip()
                mComicStrip.postValue(latestComicStrip)
                MAX_COMIC_NUMBER = latestComicStrip.num
                isLoading.postValue(false)
            } catch (e: Exception) {
                Log.e(TAG, "Exception occured = $e",e)
            }
        }
    }


    private fun fetchComicWithNumber(number: Int) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val comicStrip = repository.getComicStrip(number)
                mComicStrip.postValue(comicStrip)
                isLoading.postValue(false)
            } catch (e: Exception) {
                Log.e(TAG, "An Exception Occurred",e)
            }
        }
    }

    fun nextComicStrip() {
        Log.d(TAG, "Fetching Next comic strip")
        val comicNumber = getComicStripNumber()
        if (comicNumber != null && (comicNumber + 1 < MAX_COMIC_NUMBER)) {
            fetchComicWithNumber(comicNumber + 1)
        }
    }

    fun previousComicStrip() {
        Log.d(TAG, "Fetching previous comic strip")
        val comicNumber = getComicStripNumber()
        if (comicNumber != null && (comicNumber - 1 > 0)) {
            fetchComicWithNumber(comicNumber - 1)
        }
    }

    fun getComicStripNumber(): Int? {
        return mComicStrip.value?.num
    }

}