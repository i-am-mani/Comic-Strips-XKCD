package com.omega.xkcd.presentation.viewmodels

import android.app.Application
import android.app.Dialog
import android.util.Log
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omega.xkcd.R
import com.omega.xkcd.domain.models.ComicStripDomainModel
import com.omega.xkcd.domain.repository.ComicStripsRepository
import com.omega.xkcd.presentation.recyclerview.FavoriteComicStripAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random


class HomeViewModel(private val repository: ComicStripsRepository, application: Application) :
    AndroidViewModel(application) {

    val TAG = "HomeViewModel"
    val mComicStrip = MutableLiveData<ComicStripDomainModel>()
    private var MAX_COMIC_NUMBER = 2222
    var mState = MutableLiveData<State>(State.All)
    val isFavoriteMode: LiveData<Boolean> =
        Transformations.map(mState) { mState.value == State.Favorite }

    init {
        fetchLatestComicStrip()
    }

    private fun fetchLatestComicStrip() {
        viewModelScope.launch {
            try {
                val latestComicStrip = if (mState.value == State.All) {
                    repository.getLatestComicStrip()
                } else {
                    repository.getLatestFavoriteComicStrip()
                }

                mComicStrip.postValue(latestComicStrip)
                MAX_COMIC_NUMBER = latestComicStrip.number
            } catch (e: Exception) {
                Log.e(TAG, "Exception occurred = $e", e)
            }
        }
    }


    private fun fetchComicWithNumber(number: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val comicStrip = if (mState.value == State.All) {
                    repository.getComicStrip(number)
                } else {
                    repository.getLatestFavoriteComicStrip()
                }

                mComicStrip.postValue(comicStrip)
            } catch (e: Exception) {
                Toast.makeText(
                    getApplication(),
                    "This maybe the last comic strip",
                    Toast.LENGTH_LONG
                ).show()
                Log.e(TAG, "An Exception Occurred", e)
            }
        }
    }

    fun nextComicStrip() {
        Toast.makeText(getApplication(), "Fetching next comic", Toast.LENGTH_LONG).show()
        if (mState.value == State.All) {
            val comicNumber = getComicStripNumber()
            if (comicNumber != null && (comicNumber + 1 <= MAX_COMIC_NUMBER)) {
                fetchComicWithNumber(comicNumber + 1)
            }
        } else {
            viewModelScope.launch {
                try {
                    val favoriteComicStrip = repository.getNextFavoriteComicStrip()
                    mComicStrip.postValue(favoriteComicStrip)
                } catch (e: Exception) {
                    Log.e(TAG, "An Error Occurred $e", e)
                }
            }
        }
    }

    fun previousComicStrip() {
        Toast.makeText(getApplication(), "Fetching previous comic", Toast.LENGTH_LONG).show()
        if (mState.value == State.All) {
            val comicNumber = getComicStripNumber()
            if (comicNumber != null && (comicNumber - 1 > 0)) {
                fetchComicWithNumber(comicNumber - 1)
            }
        } else {
            viewModelScope.launch {
                try {
                    val favoriteComicStrip = repository.getPreviousFavoriteComicStrip()
                    mComicStrip.postValue(favoriteComicStrip)
                } catch (e: Exception) {
                    Log.e(TAG, "An Error Occurred $e", e)
                }
            }
        }
    }

    fun loadRandomComicStrip() {
        val nextComicNumber = Random.nextInt(MAX_COMIC_NUMBER)
        fetchComicWithNumber(nextComicNumber)
    }

    private fun getComicStripNumber(): Int? {
        return mComicStrip.value?.number
    }

    fun addToLocalDB() {
        viewModelScope.launch {
            mComicStrip.value?.let { comicStrip ->
                val response = repository.addComicStripToFavorites(comicStrip)
                if (response) {
                    comicStrip.isFavorite = true
                    Toast.makeText(getApplication(), "Added to favorites", Toast.LENGTH_LONG).show()
                }
                Log.d(TAG, "response == $response")
            }
            mComicStrip.value = mComicStrip.value
        }
    }

    fun removeFromLocalDB() {
        viewModelScope.launch {
            val comicStripDomainModel = mComicStrip.value
            if (comicStripDomainModel != null) {
                val isRemoved =
                    repository.removeComicStripFromFavorite(comicStripDomainModel)
                comicStripDomainModel.isFavorite = !isRemoved
            }
            mComicStrip.value = mComicStrip.value
        }
    }

    fun toggleFavoriteMode() {
        if (mState.value == State.All) {
            mState.value = State.Favorite
        } else {
            mState.value = State.All
        }
        fetchLatestComicStrip()
    }

    fun loadComicStrip(comicStrip: ComicStripDomainModel) {
        mComicStrip.value = comicStrip
    }

}

enum class State {
    All,
    Favorite
}