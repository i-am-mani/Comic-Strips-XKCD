package com.omega.xkcd.presentation


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.omega.xkcd.databinding.FragmentHomeBinding
import com.omega.xkcd.domain.repository.ComicStripsRepository
import com.omega.xkcd.presentation.viewmodels.HomeViewModel
import com.omega.xkcd.utils.InjectionFragment
import com.omega.xkcd.utils.KotlinViewModelProvider
import org.kodein.di.generic.instance


class HomeFragment : InjectionFragment() {

    private lateinit var mViewModel: HomeViewModel
    private val repository: ComicStripsRepository by instance()
    private val TAG = "HomeFragment"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val mainActivity = context as MainActivity
        mViewModel = KotlinViewModelProvider.of(mainActivity){
            HomeViewModel(repository,context.application)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewBinding = FragmentHomeBinding.inflate(inflater,container,false)
        viewBinding.lifecycleOwner = this
        viewBinding.viewmodel = mViewModel
        return viewBinding.root
    }


}
