package com.merseyside.partyapp.presentation.di.module

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.merseyside.partyapp.domain.interactor.AddEventInteractor
import com.merseyside.partyapp.domain.interactor.CloseEventInteractor
import com.merseyside.partyapp.domain.interactor.GetEventByIdInteractor
import com.merseyside.partyapp.presentation.view.fragment.addEvent.model.AddEventViewModel
import com.merseyside.mvvmcleanarch.presentation.fragment.BaseFragment
import com.merseyside.mvvmcleanarch.presentation.model.BundleAwareViewModelFactory
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class AddEventModule(
    private val fragment: BaseFragment,
    private val bundle: Bundle?
) {

    @Provides
    internal fun addEventViewModelProvider(
        router: Router,
        addEventUseCase: AddEventInteractor,
        getEventByIdUseCase: GetEventByIdInteractor,
        closeUseCaseUseCase: CloseEventInteractor
        ): ViewModelProvider.Factory {
        return AddEventViewModelProviderFactory(bundle, router, addEventUseCase, getEventByIdUseCase, closeUseCaseUseCase)
    }

    @Provides
    internal fun provideAddEventViewModel(factory: ViewModelProvider.Factory): AddEventViewModel {
        return ViewModelProviders.of(fragment, factory).get(AddEventViewModel::class.java)
    }

    @Provides
    internal fun provideAddEventInteractor(): AddEventInteractor {
        return AddEventInteractor()
    }

    @Provides
    internal fun provideGetEventByIdInteractor(): GetEventByIdInteractor {
        return GetEventByIdInteractor()
    }

    @Provides
    internal fun provideCloseEventInteractor(): CloseEventInteractor {
        return CloseEventInteractor()
    }

    class AddEventViewModelProviderFactory(
        bundle: Bundle?,
        private val router: Router,
        private val addEventUseCase: AddEventInteractor,
        private val getEventByIdUseCase: GetEventByIdInteractor,
        private val closeUseCaseUseCase: CloseEventInteractor
    ) : BundleAwareViewModelFactory<AddEventViewModel>(bundle) {
        override fun getViewModel(): AddEventViewModel {
            return AddEventViewModel(router, addEventUseCase, getEventByIdUseCase, closeUseCaseUseCase)
        }
    }
}