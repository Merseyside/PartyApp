package com.merseyside.partyapp.presentation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.merseyside.partyapp.domain.interactor.GetStatisticInteractor
import com.merseyside.partyapp.presentation.view.fragment.statisticMain.model.StatisticMainViewModel
import com.upstream.basemvvmimpl.presentation.fragment.BaseFragment
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class StatisticMainModule(private val fragment: BaseFragment) {

    @Provides
    internal fun itemListViewModelProvider(
        router: Router,
        getStatisticUseCase: GetStatisticInteractor
    ): ViewModelProvider.Factory {
        return StatisticMainViewModelProviderFactory(router, getStatisticUseCase)
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
        private val router: Router,
        private val getStatisticUseCase: GetStatisticInteractor
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == StatisticMainViewModel::class.java) {
                return StatisticMainViewModel(router, getStatisticUseCase) as T
            }
            throw IllegalArgumentException("Unknown class title")
        }
    }
}