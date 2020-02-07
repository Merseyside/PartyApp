package com.merseyside.partyapp.utils

import com.soywiz.klock.DateTime

fun getTimestamp(): Long {
    return DateTime.now().unixMillisLong
}
