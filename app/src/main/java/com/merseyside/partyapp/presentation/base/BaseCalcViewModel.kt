package com.merseyside.partyapp.presentation.base

import android.app.Application
import android.content.Context
import android.os.Bundle
import com.github.terrakok.cicerone.Router
import com.merseyside.archy.presentation.model.ParcelableViewModel
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.presentation.exception.ErrorMessageFactory
import com.merseyside.utils.mvvm.SingleLiveEvent

abstract class BaseCalcViewModel(application: Application, private val router: Router? = null)
    : ParcelableViewModel(application) {

    protected val errorMsgCreator = ErrorMessageFactory(application)

    val interstitialLiveEvent = SingleLiveEvent<Boolean>()

    open fun goBack() {
        router?.exit()
    }

    fun showInterstitial() {
        interstitialLiveEvent.value = true
    }

    fun logEvent(event: String, bundle: Bundle) {
        (application as CalcApplication).logFirebaseEvent(event, bundle)
    }

    override fun getLocaleContext(): Context {
        return application
    }
}