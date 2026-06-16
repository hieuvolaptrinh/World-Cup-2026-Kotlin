package com.worldcup.app.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.Date
import java.util.Locale

fun Long.toDisplayTime(): String {
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(this))
}

fun Long.toLocalMatchDate(): String {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .toString()
}
