package com.merseyside.partyapp.presentation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.merseyside.partyapp.presentation.view.fragment.statisticMember.model.StatisticMemberViewModel
import com.upstream.basemvvmimpl.presentation.fragment.BaseFragment
import dagger.Module
import dagger.Provides

@Module
class StatisticMemberModule(private val fragment: BaseFragment) {

    @Provides
    internal fun statisticMemberViewModelProvider(
    ): ViewModelProvider.Factory {
        return StatisticMemberViewModelProviderFactory()
    }

    @Provides
    internal fun provideStatisticMemberViewModel(factory: ViewModelProvider.Factory): StatisticMemberViewModel {
        return ViewModelProviders.of(fragment, factory).get(StatisticMemberViewModel::class.java)
    }

    class StatisticMemberViewModelProviderFactory: ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == StatisticMemberViewModel::class.java) {
                return StatisticMemberViewModel() as T
            }
            throw IllegalArgumentException("Unknown class title")
        }
    }
}