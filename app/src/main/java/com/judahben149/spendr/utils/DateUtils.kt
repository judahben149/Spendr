package com.judahben149.spendr.utils


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

    fun getTomorrowDateInMillis(): Long {
        val calendar = Calendar.getInstance() // Create a Calendar instance
        calendar.add(Calendar.DAY_OF_MONTH, 1)

        //sets the default date to 9am
        calendar.set(Calendar.HOUR_OF_DAY, 9)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        return calendar.timeInMillis // Get the time in milliseconds
    }

    fun getFriendlyTime(timeInMillis: Long): String {
        val currentTime = Calendar.getInstance()
        val givenTime = Calendar.getInstance()
        givenTime.timeInMillis = timeInMillis

        val format = SimpleDateFormat("h:mm a", Locale.getDefault())
        return format.format(givenTime.time)
    }



    fun getCurrentFriendlyDate(): String {
        val calendar = Calendar.getInstance() // Create a Calendar instance
        calendar.time = Date() // Set the Calendar's time to the current date

        val format = SimpleDateFormat("d MMM, yyyy", Locale.getDefault())
        return format.format(calendar.time)
    }

    fun getCurrentFriendlyDateWithTime(): String {
        val calendar = Calendar.getInstance() // Create a Calendar instance
        calendar.time = Date() // Set the Calendar's time to the current date

        val format = SimpleDateFormat("h:mm a, d MMMM, yyyy", Locale.getDefault())
        return format.format(calendar.time)
    }

    fun formatStandardDateTime(dateInMillis: Long): String {
        val currentDate = Calendar.getInstance()
        val givenDate = Calendar.getInstance()
        givenDate.timeInMillis = dateInMillis

        val format = SimpleDateFormat("d MMM, yyyy", Locale.getDefault())
        return format.format(givenDate.time)
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

        // Check if it's tomorrow
        if (currentDate.get(Calendar.YEAR) == givenDate.get(Calendar.YEAR) &&
            currentDate.get(Calendar.DAY_OF_YEAR) + 1 == givenDate.get(Calendar.DAY_OF_YEAR)
        ) {
            return "Tomorrow"
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

    fun getTimeOfDayFromDateInMillis(dateInMillis: Long): String {
        val givenDate = Calendar.getInstance()
        givenDate.timeInMillis = dateInMillis

        val timeFormat = SimpleDateFormat("h:mma", Locale.getDefault())
        return timeFormat.format(givenDate.time)
    }

    fun formatPdfFileName(dateInMillis: Long): String {
        val currentDate = Calendar.getInstance()
        val givenDate = Calendar.getInstance()
        givenDate.timeInMillis = dateInMillis

        val format = SimpleDateFormat("hh-mm_dd-MM-yyyy", Locale.getDefault())
        return format.format(givenDate.time)
    }

    fun formatGTBankDate(dateString: String): Long {
        val format = SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.getDefault())
        val date = format.parse(dateString)

        val millis = date?.time ?: 0
        return millis
    }

    fun formatAccessBankDate(dateString: String): Long {
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = format.parse(dateString)

        val millis = date?.time ?: 0
        return millis
    }
}