package com.merseyside.partyapp.presentation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.merseyside.partyapp.domain.interactor.AddEventInteractor
import com.merseyside.partyapp.presentation.view.fragment.addEvent.model.AddEventViewModel
import com.upstream.basemvvmimpl.presentation.fragment.BaseFragment
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class AddEventModule(private val fragment: BaseFragment) {

    @Provides
    internal fun addEventViewModelProvider(
        router: Router,
        addEventUseCase: AddEventInteractor
        ): ViewModelProvider.Factory {
        return AddEventViewModelProviderFactory(router, addEventUseCase)
    }

    @Provides
    internal fun provideAddEventViewModel(factory: ViewModelProvider.Factory): AddEventViewModel {
        return ViewModelProviders.of(fragment, factory).get(AddEventViewModel::class.java)
    }

    @Provides
    internal fun provideAddEventInteractor(): AddEventInteractor {
        return AddEventInteractor()
    }

    class AddEventViewModelProviderFactory(
        private val router: Router,
        private val addEventUseCase: AddEventInteractor
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == AddEventViewModel::class.java) {
                return AddEventViewModel(router, addEventUseCase) as T
            }
            throw IllegalArgumentException("Unknown class title")
        }
    }
}