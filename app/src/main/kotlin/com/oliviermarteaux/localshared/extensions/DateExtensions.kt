package com.oliviermarteaux.localshared.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZoneId
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
fun Date.toLocalDate(): LocalDate =
    this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

// Convert Date → LocalTime
@RequiresApi(Build.VERSION_CODES.O)
fun Date.toLocalTime(): LocalTime =
    this.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()

