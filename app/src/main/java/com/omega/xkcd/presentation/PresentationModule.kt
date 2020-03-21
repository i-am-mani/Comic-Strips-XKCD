package com.omega.xkcd.presentation

import androidx.fragment.app.Fragment
import com.omega.xkcd.presentation.viewmodels.HomeViewModel
import com.omega.xkcd.utils.KotlinViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

val presentationModule = Kodein.Module("Presentation"){

    bind<HomeViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton{
        KotlinViewModelProvider.of(context) {
            HomeViewModel(instance())
        }
    }
}