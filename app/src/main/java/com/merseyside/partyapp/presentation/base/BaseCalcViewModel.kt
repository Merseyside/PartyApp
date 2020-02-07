package com.merseyside.partyapp.presentation.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import com.merseyside.mvvmcleanarch.BaseApplication
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.presentation.exception.ErrorMessageFactory
import com.merseyside.mvvmcleanarch.presentation.model.ParcelableViewModel
import com.merseyside.mvvmcleanarch.utils.SingleLiveEvent
import ru.terrakok.cicerone.Router
import java.util.*

abstract class BaseCalcViewModel(private val router: Router? = null) : ParcelableViewModel() {

    final override val application = CalcApplication.getInstance()

    protected val errorMsgCreator = ErrorMessageFactory(application)

    val interstitialLiveEvent = SingleLiveEvent<Boolean>()

    open fun goBack() {
        router?.exit()
    }

    fun showInterstitial() {
        interstitialLiveEvent.value = true
    }

    override fun updateLanguage(context: Context) {}

    fun logEvent(event: String, bundle: Bundle) {
        application.logFirebaseEvent(event, bundle)
    }

    companion object {
        private const val TAG = "BaseCalcViewModel"
    }
}