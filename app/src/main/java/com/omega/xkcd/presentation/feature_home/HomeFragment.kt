package com.omega.xkcd.presentation.feature_home


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.omega.xkcd.R
import com.omega.xkcd.databinding.FragmentHomeBinding
import com.omega.xkcd.utils.InjectionFragment
import org.kodein.di.generic.instance
import org.kodein.di.generic.on


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
//        return inflater.inflate(R.layout.fragment_home, container, false)
        return viewBinding.root
    }


}
