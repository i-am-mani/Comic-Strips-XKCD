package com.omega.xkcd.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.omega.xkcd.R
import com.omega.xkcd.databinding.ItemFavoriteComicBinding
import com.omega.xkcd.domain.models.ComicStripDomainModel

class FavoriteComicStripAdapter(
    val onClick: (ComicStripDomainModel) -> Unit
) :
    RecyclerView.Adapter<FavoriteComicStripAdapter.ComicStripViewHolder>() {
    lateinit var data: List<ComicStripDomainModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicStripViewHolder {
        val itemFavoriteComicBinding: ItemFavoriteComicBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_favorite_comic,
            parent,
            false
        )
        return ComicStripViewHolder(itemFavoriteComicBinding)
    }

    override fun getItemCount(): Int {

        return if (::data.isInitialized) {
            data.count()
        } else 0
    }

    fun setDataset(data: List<ComicStripDomainModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ComicStripViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    inner class ComicStripViewHolder(private val binding: ItemFavoriteComicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: ComicStripDomainModel) {
            binding.comicStrip = data
            binding.itemCard.setOnClickListener {
                onClick(data)
            }
        }
    }


}