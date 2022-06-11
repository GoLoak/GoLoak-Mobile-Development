package com.capstone.goloak.helper

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.withNumberingFormat(): String {
    return NumberFormat.getNumberInstance(Locale("in", "ID")).format(this.toDouble())
}

fun String.withDateFormat(): String {
    val getFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale("in", "ID"))
    getFormat.timeZone = TimeZone.getTimeZone("GMT")
    val outputFormat = SimpleDateFormat("HH:mm:ss, dd MMMM yyyy", Locale("in", "ID"))
    val date = getFormat.parse(this) as Date
    outputFormat.timeZone = TimeZone.getDefault()
    return outputFormat.format(date)
}