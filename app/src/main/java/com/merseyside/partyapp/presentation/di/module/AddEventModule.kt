package com.merseyside.partyapp.presentation.di.module

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import com.merseyside.partyapp.domain.interactor.AddEventInteractor
import com.merseyside.partyapp.domain.interactor.CloseEventInteractor
import com.merseyside.partyapp.domain.interactor.GetEventByIdInteractor
import com.merseyside.partyapp.presentation.view.fragment.addEvent.model.AddEventViewModel
import com.merseyside.archy.presentation.fragment.BaseFragment
import com.merseyside.archy.presentation.model.BundleAwareViewModelFactory
import com.merseyside.partyapp.domain.interactor.GetContactsInteractor
import dagger.Module
import dagger.Provides

@Module
class AddEventModule(
    private val fragment: BaseFragment,
    private val bundle: Bundle?
) {

    @Provides
    internal fun addEventViewModelProvider(
        application: Application,
        router: Router,
        addEventUseCase: AddEventInteractor,
        getEventByIdUseCase: GetEventByIdInteractor,
        closeUseCaseUseCase: CloseEventInteractor,
        getContactsUseCase: GetContactsInteractor
        ): ViewModelProvider.Factory {
        return AddEventViewModelProviderFactory(bundle, application, router, addEventUseCase, getEventByIdUseCase, closeUseCaseUseCase, getContactsUseCase)
    }

    @Provides
    internal fun provideAddEventViewModel(factory: ViewModelProvider.Factory): AddEventViewModel {
        return ViewModelProvider(fragment, factory)[AddEventViewModel::class.java]
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

    @Provides
    internal fun provideGetContactsInteractor(): GetContactsInteractor {
        return GetContactsInteractor()
    }

    class AddEventViewModelProviderFactory(
        bundle: Bundle?,
        private val application: Application,
        private val router: Router,
        private val addEventUseCase: AddEventInteractor,
        private val getEventByIdUseCase: GetEventByIdInteractor,
        private val closeUseCaseUseCase: CloseEventInteractor,
        private val getContactsUseCase: GetContactsInteractor
    ) : BundleAwareViewModelFactory<AddEventViewModel>(bundle) {
        override fun getViewModel(): AddEventViewModel {
            return AddEventViewModel(
                application, router, addEventUseCase,
                getEventByIdUseCase, closeUseCaseUseCase, getContactsUseCase
            )
        }
    }
}