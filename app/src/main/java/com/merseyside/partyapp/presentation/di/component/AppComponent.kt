package com.merseyside.partyapp.presentation.di.component

import android.app.Application
import android.content.Context
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.merseyside.archy.presentation.di.qualifiers.ApplicationContext
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.presentation.di.module.AppModule
import com.merseyside.partyapp.presentation.di.module.NavigationModule
import com.merseyside.partyapp.utils.PrefsHelper
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NavigationModule::class])
interface AppComponent {

    fun inject(application: CalcApplication)

    fun application(): Application

    @ApplicationContext
    fun context() : Context

    fun prefsHelper(): PrefsHelper

    fun navigation() : NavigatorHolder

    fun router() : Router
}