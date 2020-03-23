package com.omega.xkcd.utils

import java.text.SimpleDateFormat
import java.util.*

object Utils {

    @JvmStatic
    fun getFormatedDate(date: Date):String{
        val dateFormatter = SimpleDateFormat("EEE, d MMM YYYY")
        return dateFormatter.format(date)
    }
}