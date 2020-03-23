package com.omega.xkcd.presentation


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omega.xkcd.R
import com.omega.xkcd.databinding.FragmentHomeBinding
import com.omega.xkcd.domain.models.ComicStripDomainModel
import com.omega.xkcd.domain.repository.ComicStripsRepository
import com.omega.xkcd.presentation.recyclerview.FavoriteComicStripAdapter
import com.omega.xkcd.presentation.viewmodels.HomeViewModel
import com.omega.xkcd.utils.InjectionFragment
import com.omega.xkcd.utils.KotlinViewModelProvider
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance


class HomeFragment : InjectionFragment() {

    private lateinit var mViewModel: HomeViewModel
    private val repository: ComicStripsRepository by instance()
    private val TAG = "HomeFragment"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val mainActivity = context as MainActivity
        mViewModel = KotlinViewModelProvider.of(mainActivity) {
            HomeViewModel(repository, context.application)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        viewBinding.lifecycleOwner = this
        viewBinding.viewmodel = mViewModel
        // TODO: Need a better way to show dialog
        viewBinding.btnShowFavComicStrips.setOnClickListener { showFavoriteComicStripsDialog() }
        return viewBinding.root
    }

    private fun showFavoriteComicStripsDialog() {
        context?.let { ctx ->
            val layoutId = R.layout.dialog_favorite_comics_list
            val dialog = Dialog(ctx)
            setDialogProperties(dialog, layoutId)

            val rv = dialog.findViewById<RecyclerView>(R.id.rvFavoriteComicStrips)

            rv.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)

            val favoriteComicStripAdapter =
                FavoriteComicStripAdapter{
                    mViewModel.loadComicStrip(it)
                    dialog.dismiss()
                }

            rv.adapter = favoriteComicStripAdapter

            lifecycleScope.launch {
                val dataset = repository.getAllFavoriteComicStrips()
                favoriteComicStripAdapter.setDataset(dataset)
            }

            dialog.show()
        }
    }

    private fun setDialogProperties(dialog: Dialog, layoutId: Int) {
        dialog.setCanceledOnTouchOutside(true)

        dialog.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS)
        dialog.setContentView(layoutId)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }


}
