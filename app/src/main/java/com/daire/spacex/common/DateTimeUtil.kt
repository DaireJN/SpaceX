package com.daire.spacex.common

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

object DateTimeUtil {
    fun getDateAsStringFromUnix(unix: Int): String {
        val time = unix * 1000L
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.UK)
        return sdf.format(Date(time))
    }

    fun getTimeAsStringFromUnix(unix: Int): String {
        val time = unix * 1000L
        val sdf = SimpleDateFormat("hh:mm:ss", Locale.UK)
        return sdf.format(Date(time))
    }

    fun getHasDateAlreadyOccurred(unix: Int): Boolean {
        val time = unix * 1000L
        val currentTime = System.currentTimeMillis()
        return time <= currentTime
    }

    fun getYearAsStringFromUnix(unix: Int): String {
        val time = unix * 1000L
        val sdf = SimpleDateFormat("yyyy", Locale.UK)
        return sdf.format(Date(time))
    }

    fun getDaysSinceUnix(unix: Int): String {
        val time = unix * 1000L
        val difference = abs(time.minus(System.currentTimeMillis()))
        return TimeUnit.MILLISECONDS.toDays(difference).toString()
    }
}