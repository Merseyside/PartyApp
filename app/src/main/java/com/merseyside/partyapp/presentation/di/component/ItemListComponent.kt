package com.merseyside.partyapp.presentation.di.component

import com.merseyside.partyapp.presentation.di.module.ItemListModule
import com.merseyside.partyapp.presentation.view.fragment.itemList.view.ItemListFragment
import com.merseyside.archy.presentation.di.qualifiers.FragmentScope
import dagger.Component

@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [ItemListModule::class])
interface ItemListComponent {

    fun inject(fragment: ItemListFragment)
}