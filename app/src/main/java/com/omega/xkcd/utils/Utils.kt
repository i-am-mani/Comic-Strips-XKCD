package com.omega.xkcd.utils

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.graphics.ColorFilter
import android.widget.TextView
import android.widget.Toast
import com.omega.xkcd.ComicStripsApplication
import com.omega.xkcd.R
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    @JvmStatic
    fun getFormatedDate(date: Date?):String{
        if(date != null){
        val dateFormatter = SimpleDateFormat("EEE, d MMM YYYY")
        return dateFormatter.format(date)}
        return ""
    }
}