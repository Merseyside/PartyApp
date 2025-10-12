package com.merseyside.partyapp.utils

import com.merseyside.utils.preferences.PreferenceManager


class PrefsHelper(private val preferenceManager: PreferenceManager) {

    fun setCurrency(value : String) {
        preferenceManager.put(CURRENCY_KEY, value)
    }

    fun getCurrency(): String {
        return preferenceManager.getString(CURRENCY_KEY, "")
    }

    fun isRated(): Boolean {
        return preferenceManager.getBool(RATE_KEY, false)
    }

    fun setRated(isRated: Boolean) {
        preferenceManager.put(RATE_KEY, isRated)
    }

    fun setServiceFee(serviceFee: Float) {
        preferenceManager.put(SERVICE_KEY, serviceFee)
    }

    fun getServiceFee(): Float {
        return preferenceManager.getFloat(SERVICE_KEY, 0f)
    }

    companion object {
        private const val CURRENCY_KEY = "currency"
        private const val RATE_KEY = "isRated"
        private const val SERVICE_KEY = "service_fee"
    }
}