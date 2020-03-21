package com.omega.xkcd.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide


private val TAG = "BindingAdapter"

@BindingAdapter("app:showImage")
fun showImageFromUrl(view: ImageView, url: String?) {

    if (url != null) {
        val circularProgressDrawable =
            CircularProgressDrawable(view.context)
        circularProgressDrawable.strokeWidth = 10f
        circularProgressDrawable.centerRadius = 60f
        circularProgressDrawable.start()
        Glide.with(view).load(url).placeholder(circularProgressDrawable).into(view)
        Log.d(TAG, "binding image view with image from glide")
    }
    Log.d(TAG, "url = $url")
}

