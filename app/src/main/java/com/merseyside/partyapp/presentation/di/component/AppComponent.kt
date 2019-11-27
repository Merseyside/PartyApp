package com.merseyside.partyapp.presentation.di.component

import android.app.Application
import android.content.Context
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.presentation.di.module.AppModule
import com.merseyside.partyapp.presentation.di.module.NavigationModule
import com.merseyside.partyapp.utils.PrefsHelper
import com.merseyside.mvvmcleanarch.presentation.di.qualifiers.ApplicationContext
import com.merseyside.mvvmcleanarch.utils.PreferenceManager
import dagger.Component
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NavigationModule::class])
interface AppComponent {

    fun inject(application: CalcApplication)

    @ApplicationContext
    fun context() : Context

    fun application() : Application

    fun getPrefsHelper(): PrefsHelper

    fun navigation() : NavigatorHolder

    fun router() : Router
}