package com.eastx7.weather.utilities

import android.content.Context
import androidx.room.TypeConverter
import com.eastx7.weather.R
import java.util.*

class Converters {

    @TypeConverter
    fun strToBoolean(value: String?) =
        when (value) {
            null -> false
            "1" -> true
            else -> false
        }

    fun boolToString(value: Boolean) = if (value) "1" else "0"

    fun epochToDayId(epoch: Long): Int {

        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(epoch * 1000)

        val days = calendar[Calendar.DAY_OF_YEAR]
        val year = calendar[Calendar.YEAR]

        return year * 1000 + days
    }

    private fun humanMonthOfEpoch(epoch: Long, context: Context): String {

        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(epoch * 1000)

        return when (calendar[Calendar.MONTH] + 1) {
            1 -> context.resources.getString(R.string.january)
            2 -> context.resources.getString(R.string.february)
            3 -> context.resources.getString(R.string.march)
            4 -> context.resources.getString(R.string.april)
            5 -> context.resources.getString(R.string.may)
            6 -> context.resources.getString(R.string.june)
            7 -> context.resources.getString(R.string.july)
            8 -> context.resources.getString(R.string.august)
            9 -> context.resources.getString(R.string.september)
            10 -> context.resources.getString(R.string.october)
            11 -> context.resources.getString(R.string.november)
            12 -> context.resources.getString(R.string.december)
            else -> ""
        }
    }

    fun epochToHumanDate(epoch: Long, context: Context): String {

        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(epoch * 1000)

        val day = calendar[Calendar.DAY_OF_MONTH]
        //val year = calendar[Calendar.YEAR]

        return "${humanMonthOfEpoch(epoch, context)}, $day"
    }
}
