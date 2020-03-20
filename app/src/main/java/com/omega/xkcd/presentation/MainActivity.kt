package com.omega.xkcd.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.omega.xkcd.R
import com.omega.xkcd.data.room.ComicStripsDatabase
import com.omega.xkcd.domain.repository.ComicStripsRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.description
import org.kodein.di.generic.constant
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

class MainActivity : AppCompatActivity(),KodeinAware {

    private val parentKodein by kodein()

//    @SuppressWarnings("LeakingThisInConstructor")
    final override val kodeinContext = kcontext<AppCompatActivity>(this)

    // Using retainedKodein will not recreate Kodein when the Activity restarts
    final override val kodein: Kodein by retainedKodein {
        extend(parentKodein)
    }

    private val repository: ComicStripsRepository by instance()
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GlobalScope.launch {
            val latestComicStrip = repository.getLatestComicStrip()
            Log.d(TAG, "output = ${latestComicStrip.title}")
            Log.d(TAG, "path = ${latestComicStrip.path}")
        }
        settupNavigationBar()
    }

    fun settupNavigationBar(){
        val navController = navHostFragment.findNavController()
        bottomNav.setupWithNavController(navController)
//        navController.navigate(R.id.home)
    }

}