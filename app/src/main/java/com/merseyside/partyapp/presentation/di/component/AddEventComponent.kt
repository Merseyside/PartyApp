package com.merseyside.partyapp.presentation.di.component

import com.merseyside.partyapp.presentation.di.module.AddEventModule
import com.merseyside.partyapp.presentation.view.fragment.addEvent.view.AddEventFragment
import com.upstream.basemvvmimpl.presentation.di.qualifiers.FragmentScope
import dagger.Component

@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [AddEventModule::class])
interface AddEventComponent {

    fun inject(fragment: AddEventFragment)
}