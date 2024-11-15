package com.kuznetsov.taskmap.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object MyDateUtils {

    fun millisToFormatDate(millis: Long): String {
        val date = Date(millis)
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        return sdf.format(date)
    }

    fun millisToFormatDateWithTime(millis: Long): String {
        val date = Date(millis)
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("GMT+03")
        return sdf.format(date)
    }

    fun getCurrentDateInMillis(): Long {
        return System.currentTimeMillis();
    }

}