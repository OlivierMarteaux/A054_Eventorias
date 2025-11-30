package com.oliviermarteaux.localshared.utils

import com.oliviermarteaux.localshared.extensions.toDate
import java.time.LocalDate
import java.util.Date

fun randomDate(minYear: Int = 2025, maxYear: Int = 2027): Date {
    val year = (minYear..maxYear).random()
    val month = (1..12).random()
    val day = (1..28).random()
    return LocalDate.of(year, month, day).toDate()
}