package com.oliviermarteaux.localshared.utils

import com.oliviermarteaux.localshared.extensions.toDate
import java.time.LocalTime
import java.util.Date

fun randomTime(minHour: Int = 9, maxHour: Int = 22): Date {
    val hour = (minHour..maxHour).random()
    val minute = ((0..59).random()) / 10 * 10
    return LocalTime.of(hour,minute).toDate()
}