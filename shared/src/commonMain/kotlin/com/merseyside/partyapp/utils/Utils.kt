package com.merseyside.partyapp.utils

import com.soywiz.klock.DateTime

fun getTimestamp(): Long {
    return DateTime.now().unixMillisLong
}

fun generateId(): Long {
    val charPool : List<Char> = ('0'..'9').toList()

    return (1..10)
        .map { kotlin.random.Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
        .toLong()
}