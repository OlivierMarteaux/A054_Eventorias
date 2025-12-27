package com.oliviermarteaux.localshared.extensions

//import java.text.ParseException
//import java.text.SimpleDateFormat
//import java.util.*
//
//fun String.toDateTypeDate(): Date {
//    val formats = listOf(
//        SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE),   // French format
//        SimpleDateFormat("MM/dd/yyyy", Locale.US)       // US/UK format
//    )
//
//    for (format in formats) {
//        try {
//            return format.parse(this)?: Date()
//        } catch (e: ParseException) {
//            // Try the next format
//        }
//    }
//    return Date() // Return null if no format matches
//}
//
//fun String.toDateTypeTime(): Date {
//    val formats = listOf(
//        SimpleDateFormat("HH:mm", Locale.getDefault()),   // 24-hour format
//        SimpleDateFormat("hh:mm a", Locale.US)           // 12-hour format with AM/PM
//    )
//
//    for (format in formats) {
//        try {
//            return format.parse(this)?: Date()
//        } catch (e: ParseException) {
//            // Try next format
//        }
//    }
//    return Date()
//}