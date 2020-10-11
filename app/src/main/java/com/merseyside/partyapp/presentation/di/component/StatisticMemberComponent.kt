package com.merseyside.partyapp.presentation.di.component

import com.merseyside.partyapp.presentation.di.module.StatisticMemberModule
import com.merseyside.partyapp.presentation.view.fragment.statisticMember.view.StatisticMemberFragment
import com.merseyside.archy.presentation.di.qualifiers.FragmentScope
import dagger.Component

@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [StatisticMemberModule::class])
interface StatisticMemberComponent {

    fun inject(fragment: StatisticMemberFragment)
}