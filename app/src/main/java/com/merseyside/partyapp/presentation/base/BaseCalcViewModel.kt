package com.merseyside.partyapp.presentation.base

import android.content.Context
import android.os.Bundle
import com.merseyside.merseyLib.presentation.model.ParcelableViewModel
import com.merseyside.merseyLib.utils.mvvm.SingleLiveEvent
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.presentation.exception.ErrorMessageFactory
import ru.terrakok.cicerone.Router

abstract class BaseCalcViewModel(private val router: Router? = null) : ParcelableViewModel() {

    val application = CalcApplication.getInstance()

    protected val errorMsgCreator = ErrorMessageFactory(application)

    val interstitialLiveEvent = SingleLiveEvent<Boolean>()

    open fun goBack() {
        router?.exit()
    }

    fun showInterstitial() {
        interstitialLiveEvent.value = true
    }

    fun logEvent(event: String, bundle: Bundle) {
        application.logFirebaseEvent(event, bundle)
    }

    override fun getLocaleContext(): Context {
        return application
    }
}