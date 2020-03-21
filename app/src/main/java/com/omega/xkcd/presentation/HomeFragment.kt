package com.omega.xkcd.presentation


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.omega.xkcd.databinding.FragmentHomeBinding
import com.omega.xkcd.presentation.viewmodels.HomeViewModel
import com.omega.xkcd.utils.InjectionFragment
import org.kodein.di.generic.instance


class HomeFragment : InjectionFragment() {

    private val mViewModel: HomeViewModel by instance()
    private val TAG = "HomeFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewBinding = FragmentHomeBinding.inflate(inflater,container,false)
        viewBinding.lifecycleOwner = this
        viewBinding.viewmodel = mViewModel
        Log.d(TAG, "Home Fragment")
        return viewBinding.root
    }


}
