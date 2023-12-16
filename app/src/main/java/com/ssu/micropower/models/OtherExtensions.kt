package com.ssu.micropower.models

import com.ssu.micropower.app.Constants
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

fun String.toDate(): Date {
    val dateParts = this.split(" ")
    if (dateParts.size != 2) {
        return Date()
    }

    val calendar = Calendar.getInstance().apply { time = Date() }
    val date = dateParts[0]
        .plus(".")
        .plus(calendar.get(Calendar.YEAR))
        .plus(" ")
        .plus(dateParts[1])

    return SimpleDateFormat("dd.MM.yyyy HH:mm").parse(date)
}

fun String.asDeviceName(): String {
    return if (this.length > Constants.DEVICE_NAME_MAX_LENGTH) {
        this.substring(0, Constants.DEVICE_NAME_MAX_LENGTH).plus("…")
    } else {
        this
    }
}