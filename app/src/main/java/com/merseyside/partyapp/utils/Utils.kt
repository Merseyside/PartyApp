package com.merseyside.partyapp.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun getDateTime(timestamp: Long): String? {
    return try {
        val sdf = SimpleDateFormat("DD MMM")
        val netDate = Date(timestamp)
        sdf.format(netDate)
    } catch (e: Exception) {
        e.toString()
    }
}