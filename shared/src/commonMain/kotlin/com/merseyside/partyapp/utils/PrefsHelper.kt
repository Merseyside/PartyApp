package com.merseyside.partyapp.utils

import com.russhwolf.settings.Settings


class PreferenceHelper(private val settings: Settings) {

    fun getCurrency(defaultValue: String): String {
        return settings.getString("currency", defaultValue)
    }

    fun setCurrency(value: String) {
        settings.putString("currency", value)
    }
}