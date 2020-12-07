package com.merseyside.partyapp.presentation.di.module

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.merseyside.partyapp.presentation.view.fragment.statisticMember.model.StatisticMemberViewModel
import com.merseyside.archy.presentation.fragment.BaseFragment
import com.merseyside.archy.presentation.model.BundleAwareViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class StatisticMemberModule(
    private val fragment: BaseFragment,
    private val bundle: Bundle?
) {
    @Provides
    internal fun statisticMemberViewModelProvider(
        application: Application
    ): ViewModelProvider.Factory {
        return StatisticMemberViewModelProviderFactory(bundle, application)
    }

    @Provides
    internal fun provideStatisticMemberViewModel(factory: ViewModelProvider.Factory): StatisticMemberViewModel {
        return ViewModelProvider(fragment, factory)[StatisticMemberViewModel::class.java]
    }

    class StatisticMemberViewModelProviderFactory(
        bundle: Bundle?,
        private val application: Application
    ): BundleAwareViewModelFactory<StatisticMemberViewModel>(bundle) {
        override fun getViewModel(): StatisticMemberViewModel {
            return StatisticMemberViewModel(application)
        }
    }
}