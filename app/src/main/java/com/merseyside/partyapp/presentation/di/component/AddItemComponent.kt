package com.merseyside.partyapp.presentation.di.component

import com.merseyside.partyapp.presentation.di.module.AddItemModule
import com.merseyside.partyapp.presentation.view.fragment.addItem.view.AddItemFragment
import com.merseyside.merseyLib.presentation.di.qualifiers.FragmentScope
import dagger.Component

@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [AddItemModule::class])
interface AddItemComponent {

    fun inject(fragment: AddItemFragment)
}