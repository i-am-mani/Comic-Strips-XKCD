package com.omega.xkcd.utils

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.kcontext


abstract class InjectionFragment : Fragment(), KodeinAware {

    final override val kodeinContext = kcontext<Fragment>(this)

    final override val kodein: Kodein by kodein()
}
