package com.oliviermarteaux.localshared.extensions

import android.content.Context
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.core.net.toUri

fun @receiver:DrawableRes Int.toUriString(context: Context): String {
    return "android.resource://${context.packageName}/$this"
}