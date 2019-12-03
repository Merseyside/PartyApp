package com.merseyside.partyapp.utils

import com.merseyside.mvvmcleanarch.utils.PreferenceManager

class PrefsHelper(private val preferenceManager: PreferenceManager) {

    fun setCurrency(value : String) {
        preferenceManager.savePreference(CURRENCY_KEY, value)
    }

    fun getCurrency(): String {
        return preferenceManager.getStringPreference(CURRENCY_KEY, "")
    }

    fun isRated(): Boolean {
        return preferenceManager.getBoolPreference(RATE_KEY, false)
    }

    fun setRated(isRated: Boolean) {
        preferenceManager.savePreference(RATE_KEY, isRated)
    }

    companion object {
        private const val CURRENCY_KEY = "currency"
        private const val RATE_KEY = "isRated"
    }
}