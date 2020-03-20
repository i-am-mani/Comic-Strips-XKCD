package com.omega.xkcd.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("app:showImage")
fun showImageFromUrl(view: ImageView, url: String?) {
    val TAG = "BindingAdapter"
    if(url != null) {
        Glide.with(view).load(url).into(view)
        Log.d(TAG, "binding image view with image from glide")
    }
    Log.d(TAG, "url = $url")
}

