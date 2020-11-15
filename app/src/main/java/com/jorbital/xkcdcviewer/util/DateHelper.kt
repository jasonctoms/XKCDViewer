package com.jorbital.xkcdcviewer.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

object DateHelper {
    fun formattedDate(year: Int, month: Int, day: Int): String {
        //this is super hacky and I don't like it, but dates are annoying and would rather spend time on android things
        val date = LocalDate.parse("$year-${month.fixDayAndMonth()}-${day.fixDayAndMonth()}")
        return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
    }

    private fun Int.fixDayAndMonth(): String {
        if (this.toString().length == 1) return "0$this"
        return this.toString()
    }
}