package com.judahben149.spendr.utils


import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {

    fun getCurrentDateInMillis(): Long {
        val calendar = Calendar.getInstance() // Create a Calendar instance
        calendar.time = Date() // Set the Calendar's time to the current date

        return calendar.timeInMillis // Get the time in milliseconds
    }

    fun formatFriendlyDateTime(dateInMillis: Long): String {
        val currentDate = Calendar.getInstance()
        val givenDate = Calendar.getInstance()
        givenDate.timeInMillis = dateInMillis

        // Check if it's yesterday
        if (currentDate.get(Calendar.YEAR) == givenDate.get(Calendar.YEAR) &&
            currentDate.get(Calendar.DAY_OF_YEAR) - 1 == givenDate.get(Calendar.DAY_OF_YEAR)
        ) {
            return "Yesterday"
        }

        // Check if it's today
        if (currentDate.get(Calendar.YEAR) == givenDate.get(Calendar.YEAR) &&
            currentDate.get(Calendar.DAY_OF_YEAR) == givenDate.get(Calendar.DAY_OF_YEAR)
        ) {
            return "Today"
        }

        //If date is part of current year, return just the day and month
        if(currentDate.get(Calendar.YEAR) == givenDate.get(Calendar.YEAR)) {
            val format = SimpleDateFormat("d MMM", Locale.getDefault())
            return format.format(givenDate.time)
        }

        val format = SimpleDateFormat("d MMM, yyyy", Locale.getDefault())
        return format.format(givenDate.time)
    }

    fun getMonthFromDateInMillis(dateInMillis: Long): String {
        val currentDate = Calendar.getInstance()
        val givenDate = Calendar.getInstance()
        givenDate.timeInMillis = dateInMillis

        if (givenDate.get(Calendar.YEAR) != currentDate.get(Calendar.YEAR)) {
            val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
            return monthYearFormat.format(givenDate.time)
        }

        val monthFormat = SimpleDateFormat("MMMM", Locale.getDefault())
        return monthFormat.format(givenDate.time)
    }

}