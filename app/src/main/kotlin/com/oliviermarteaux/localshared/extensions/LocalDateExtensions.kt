package com.oliviermarteaux.localshared.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toDate(): Date =
    Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())