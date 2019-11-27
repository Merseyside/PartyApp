package com.merseyside.partyapp.presentation.di.component

import com.merseyside.partyapp.presentation.di.module.MainModule
import com.merseyside.partyapp.presentation.view.activity.main.view.MainActivity
import com.merseyside.mvvmcleanarch.presentation.di.qualifiers.ActivityScope
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [MainModule::class])
interface MainComponent {

    fun inject(activity: MainActivity)
}