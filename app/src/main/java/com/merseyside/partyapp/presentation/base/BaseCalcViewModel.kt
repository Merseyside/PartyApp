package com.merseyside.partyapp.presentation.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.presentation.exception.ErrorMessageFactory
import com.merseyside.mvvmcleanarch.presentation.model.ParcelableViewModel
import com.merseyside.mvvmcleanarch.utils.SingleLiveEvent
import ru.terrakok.cicerone.Router
import java.util.*

abstract class BaseCalcViewModel(private val router: Router? = null) : ParcelableViewModel() {

    protected val context = CalcApplication.getInstance()
    protected val errorMsgCreator = ErrorMessageFactory(context)

    val interstitialLiveEvent = SingleLiveEvent<Boolean>()

    protected fun getString(@StringRes id: Int, vararg args: String): String {
        return getString(context, id, *args)
    }

    open fun goBack() {
        router?.exit()
    }

    fun showInterstitial() {
        interstitialLiveEvent.value = true
    }

    override fun updateLanguage(context: Context) {}

    fun logEvent(event: String, bundle: Bundle) {
        context.logFirebaseEvent(event, bundle)
    }

    companion object {
        private const val TAG = "BaseCalcViewModel"
    }
}