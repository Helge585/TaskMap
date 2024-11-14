package com.kuznetsov.taskmap.utils

import java.text.SimpleDateFormat
import java.util.Date

object MyDateUtils {

    fun millisToFormatDate(millis: Long): String {
        val date = Date(millis)
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        return sdf.format(date)
    }

    fun getCurrentDateInMillis(): Long {
        return System.currentTimeMillis();
    }
}