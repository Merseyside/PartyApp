package com.merseyside.partyapp.presentation.di.component

import com.merseyside.partyapp.presentation.di.module.StatisticMainModule
import com.merseyside.partyapp.presentation.view.fragment.statisticMain.view.StatisticMainFragment
import com.upstream.basemvvmimpl.presentation.di.qualifiers.FragmentScope
import dagger.Component

@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [StatisticMainModule::class])
interface StatisticMainComponent {

    fun inject(fragment: StatisticMainFragment)
}