package com.merseyside.partyapp.presentation.view.activity.main.model

import android.content.Context
import android.os.Bundle
import com.merseyside.partyapp.presentation.base.BaseCalcViewModel
import com.merseyside.partyapp.presentation.navigation.Screens
import ru.terrakok.cicerone.Router

class MainViewModel(private val router: Router) : BaseCalcViewModel(router) {
    override fun readFrom(bundle: Bundle) {}

    override fun writeTo(bundle: Bundle) {}

    override fun dispose() {}

    fun navigateToEventList() {
        router.newRootScreen(Screens.EventListScreen())
    }
}