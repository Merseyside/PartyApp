package com.merseyside.partyapp.presentation.di.module

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.merseyside.partyapp.domain.interactor.GetStatisticInteractor
import com.merseyside.partyapp.presentation.view.fragment.statisticMain.model.StatisticMainViewModel
import com.merseyside.mvvmcleanarch.presentation.fragment.BaseFragment
import com.merseyside.mvvmcleanarch.presentation.model.BundleAwareViewModelFactory
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class StatisticMainModule(
    private val fragment: BaseFragment,
    private val bundle: Bundle?
) {

    @Provides
    internal fun itemListViewModelProvider(
        router: Router,
        getStatisticUseCase: GetStatisticInteractor
    ): ViewModelProvider.Factory {
        return StatisticMainViewModelProviderFactory(bundle, router, getStatisticUseCase)
    }

    @Provides
    internal fun provideStatisticMainViewModel(factory: ViewModelProvider.Factory): StatisticMainViewModel {
        return ViewModelProviders.of(fragment, factory).get(StatisticMainViewModel::class.java)
    }

    @Provides
    internal fun provideGetStatisticInteractor(): GetStatisticInteractor {
        return GetStatisticInteractor()
    }

    class StatisticMainViewModelProviderFactory(
        bundle: Bundle?,
        private val router: Router,
        private val getStatisticUseCase: GetStatisticInteractor
    ) : BundleAwareViewModelFactory<StatisticMainViewModel>(bundle) {
        override fun getViewModel(): StatisticMainViewModel {
            return StatisticMainViewModel(router, getStatisticUseCase)
        }
    }
}