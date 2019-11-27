package com.merseyside.partyapp.presentation.di.module

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.merseyside.partyapp.presentation.view.fragment.statisticMember.model.StatisticMemberViewModel
import com.merseyside.mvvmcleanarch.presentation.fragment.BaseFragment
import com.merseyside.mvvmcleanarch.presentation.model.BundleAwareViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class StatisticMemberModule(
    private val fragment: BaseFragment,
    private val bundle: Bundle?
) {

    @Provides
    internal fun statisticMemberViewModelProvider(
    ): ViewModelProvider.Factory {
        return StatisticMemberViewModelProviderFactory(bundle)
    }

    @Provides
    internal fun provideStatisticMemberViewModel(factory: ViewModelProvider.Factory): StatisticMemberViewModel {
        return ViewModelProviders.of(fragment, factory).get(StatisticMemberViewModel::class.java)
    }

    class StatisticMemberViewModelProviderFactory(bundle: Bundle?): BundleAwareViewModelFactory<StatisticMemberViewModel>(bundle) {
        override fun getViewModel(): StatisticMemberViewModel {
            return StatisticMemberViewModel()
        }
    }
}