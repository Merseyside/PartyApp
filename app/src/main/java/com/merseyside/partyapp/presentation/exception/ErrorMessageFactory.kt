package com.merseyside.partyapp.presentation.exception

import android.content.Context
import com.merseyside.partyapp.BuildConfig
import com.merseyside.partyapp.R

class ErrorMessageFactory(private val context: Context) {

    private val TAG = javaClass.simpleName

    fun createErrorMsg(throwable: Throwable): String {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace()
        }

        return throwable.message ?: getString(R.string.unknown_error)
    }

    private fun getString(id: Int): String {
        return context.getString(id)
    }
}