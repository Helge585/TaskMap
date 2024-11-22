package com.kuznetsov.taskmap.utils

object MyStringUtils {

    fun getPercentText(currentResult: Long, finishResult: Long): String {
        return String.format("%.2f", currentResult.toDouble() / finishResult * 100) +
                "%\n($currentResult from $finishResult)"
    }
}