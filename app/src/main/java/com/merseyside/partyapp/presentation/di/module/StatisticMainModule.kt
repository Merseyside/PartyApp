package com.merseyside.partyapp.presentation.di.module

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import com.merseyside.partyapp.domain.interactor.GetStatisticInteractor
import com.merseyside.partyapp.presentation.view.fragment.statisticMain.model.StatisticMainViewModel
import com.merseyside.archy.presentation.fragment.BaseFragment
import com.merseyside.archy.presentation.model.BundleAwareViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class StatisticMainModule(
    private val fragment: BaseFragment,
    private val bundle: Bundle?
) {

    @Provides
    internal fun itemListViewModelProvider(
        router: Router,
        application: Application,
        getStatisticUseCase: GetStatisticInteractor
    ): ViewModelProvider.Factory {
        return StatisticMainViewModelProviderFactory(bundle, application, router, getStatisticUseCase)
    }

    @Provides
    internal fun provideStatisticMainViewModel(factory: ViewModelProvider.Factory): StatisticMainViewModel {
        return ViewModelProvider(fragment, factory)[StatisticMainViewModel::class.java]
    }

    @Provides
    internal fun provideGetStatisticInteractor(): GetStatisticInteractor {
        return GetStatisticInteractor()
    }

    class StatisticMainViewModelProviderFactory(
        bundle: Bundle?,
        private val application: Application,
        private val router: Router,
        private val getStatisticUseCase: GetStatisticInteractor
    ) : BundleAwareViewModelFactory<StatisticMainViewModel>(bundle) {
        override fun getViewModel(): StatisticMainViewModel {
            return StatisticMainViewModel(application, router, getStatisticUseCase)
        }
    }
}