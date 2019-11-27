package com.merseyside.partyapp.utils

import com.github.florent37.preferences.Preferences

class PreferenceHelper(private val preferences: Preferences) {

    fun getCurrency(defaultValue: String): String {
        return preferences.getString("currency", defaultValue)
    }

    fun setCurrency(value: String) {
        preferences.setString("currency", value)
    }
}