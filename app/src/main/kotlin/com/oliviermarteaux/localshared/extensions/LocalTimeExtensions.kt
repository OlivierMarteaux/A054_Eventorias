package com.oliviermarteaux.localshared.extensions

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.time.ZoneId
import java.util.Date

// Convert LocalTime → Date
fun LocalTime.toDate(): Date =
    Date.from(this.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant())

fun LocalTime.toLocalTimeString(locale: Locale = Locale.getDefault()): String {
    val pattern = when (locale.language.lowercase()) {
        // French → 24h format "HH:mm"
        "fr" -> "HH:mm"

        // UK → 12h format "hh:mm a"
        "en" -> when (locale.country.uppercase()) {
            "GB" -> "hh:mm a"
            else -> "HH:mm" // fallback for other EN locales
        }

        // Fallback for all other locales
        else -> "HH:mm"
    }

    val formatter = DateTimeFormatter.ofPattern(pattern, locale)
    return this.format(formatter)
}