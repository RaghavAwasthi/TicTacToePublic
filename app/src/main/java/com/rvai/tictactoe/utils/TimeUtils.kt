package com.rvai.tictactoe.utils

import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.zone.ZoneRulesException

object TimeUtils {
    @JvmStatic
    val currentDateTime: Long
        get() = Instant.now().toEpochMilli()

    fun getLocalDateTime(datetime: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(datetime), ZoneId.systemDefault())
    }

    fun getDateTime(date: Long): String {
        var mdate = ""
        try {
            val dateTime = getLocalDateTime(date)
            mdate = dateTime.month.toString() + " " + dateTime.dayOfMonth + "," + dateTime.year + " " + dateTime.hour + ":" + dateTime.minute
        } catch (e: ZoneRulesException) {
        }
        return mdate
    }
}