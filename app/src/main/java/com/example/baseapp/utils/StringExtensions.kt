package com.worldcup.app.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

data class LocalMatchDateTime(
    val date: LocalDate,
    val displayDate: String,
    val displayTime: String
)

fun String.decodeUnicodeFlag(): String {
    if (isBlank()) return ""

    val unicodePattern = Regex("""\\u\{([0-9A-Fa-f]+)\}""")
    val matches = unicodePattern.findAll(this).toList()

    if (matches.isEmpty()) return this

    return matches.joinToString(separator = "") { match ->
        val codePoint = match.groupValues[1].toInt(16)
        String(Character.toChars(codePoint))
    }
}

fun formatMatchDateTime(date: String, time: String): LocalMatchDateTime {
    return try {
        val sourceDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
        val sourceTime =
            LocalTime.parse(time.substringBefore(" "), DateTimeFormatter.ofPattern("H:mm"))
        val sourceOffset = parseUtcOffset(time.substringAfter("UTC", "UTC+0").trim())
        val localDateTime =
            OffsetDateTime
                .of(LocalDateTime.of(sourceDate, sourceTime), sourceOffset)
                .atZoneSameInstant(ZoneId.systemDefault())

        LocalMatchDateTime(
            date = localDateTime.toLocalDate(),
            displayDate = localDateTime.format(
                DateTimeFormatter.ofPattern(
                    "MM/dd",
                    Locale.getDefault()
                )
            ),
            displayTime = localDateTime.format(
                DateTimeFormatter.ofPattern(
                    "HH:mm",
                    Locale.getDefault()
                )
            )
        )
    } catch (e: Exception) {
        LocalMatchDateTime(
            date = runCatching { LocalDate.parse(date) }.getOrElse { LocalDate.now() },
            displayDate = date,
            displayTime = time.substringBefore(" ")
        )
    }
}

fun parseUtcOffset(offsetText: String): ZoneOffset {
    val normalized = offsetText.ifBlank { "+0" }
    val sign = if (normalized.startsWith("-")) "-" else "+"
    val rawHours = normalized.removePrefix("+").removePrefix("-")
    val parts = rawHours.split(":")
    val hours = parts.getOrNull(0)?.toIntOrNull() ?: 0
    val minutes = parts.getOrNull(1)?.toIntOrNull() ?: 0

    return ZoneOffset.of(String.format(Locale.US, "%s%02d:%02d", sign, hours, minutes))
}
