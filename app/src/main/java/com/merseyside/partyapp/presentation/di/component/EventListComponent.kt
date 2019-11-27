package com.merseyside.partyapp.presentation.di.component

import com.merseyside.partyapp.presentation.di.module.EventListModule
import com.merseyside.partyapp.presentation.view.fragment.eventList.view.EventListFragment
import com.merseyside.mvvmcleanarch.presentation.di.qualifiers.FragmentScope
import dagger.Component

@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [EventListModule::class])
interface EventListComponent {

    fun inject(fragmnent: EventListFragment)
}