package com.merseyside.partyapp.presentation.view.activity.main.model

import android.app.Application
import android.os.Bundle
import com.github.terrakok.cicerone.Router
import com.merseyside.partyapp.presentation.base.BaseCalcViewModel
import com.merseyside.partyapp.presentation.navigation.Screens

class MainViewModel(
    application: Application,
    private val router: Router
    ) : BaseCalcViewModel(application, router) {
    override fun readFrom(bundle: Bundle) {}

    override fun writeTo(bundle: Bundle) {}

    override fun dispose() {}

    fun navigateToEventList() {
        router.newRootScreen(Screens.EventListScreen())
    }
}