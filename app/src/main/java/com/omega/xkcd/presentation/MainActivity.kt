package com.omega.xkcd.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.omega.xkcd.R
import com.omega.xkcd.domain.repository.ComicStripsRepository
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

class MainActivity : AppCompatActivity(),KodeinAware {

    private val parentKodein by kodein()

    final override val kodeinContext = kcontext<AppCompatActivity>(this)

    final override val kodein: Kodein by retainedKodein {
        extend(parentKodein)
    }

    private val repository: ComicStripsRepository by instance()
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupFragment()
    }

    private fun setupFragment(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val homeFragment = HomeFragment()
        fragmentTransaction.replace(R.id.fragmentContainer,homeFragment)
        fragmentTransaction.commit()
    }
}