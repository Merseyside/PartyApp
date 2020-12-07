package com.merseyside.partyapp.presentation.di.module

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import com.merseyside.partyapp.domain.interactor.DeleteEventInteractor
import com.merseyside.partyapp.domain.interactor.GetEventsInteractor
import com.merseyside.partyapp.presentation.view.fragment.eventList.model.EventListViewModel
import com.merseyside.archy.presentation.fragment.BaseFragment
import com.merseyside.archy.presentation.model.BundleAwareViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class EventListModule(
    private val fragment: BaseFragment,
    private val bundle: Bundle?
) {

    @Provides
    internal fun eventListViewModelProvider(
        application: Application,
        router: Router,
        getEventsUseCase: GetEventsInteractor,
        deleteEventUseCase: DeleteEventInteractor
    ): ViewModelProvider.Factory {
        return EventListViewModelProviderFactory(bundle, application, router, getEventsUseCase, deleteEventUseCase)
    }

    @Provides
    internal fun provideEventListViewModel(factory: ViewModelProvider.Factory): EventListViewModel {
        return ViewModelProvider(fragment, factory)[EventListViewModel::class.java]
    }

    @Provides
    internal fun provideDeleteEventInteractor(): DeleteEventInteractor {
        return DeleteEventInteractor()
    }

    @Provides
    internal fun provideGetEventsInteractor(): GetEventsInteractor {
        return GetEventsInteractor()
    }

    class EventListViewModelProviderFactory(
        bundle: Bundle?,
        private val application: Application,
        private val router: Router,
        private val getEventsUseCase: GetEventsInteractor,
        private val deleteEventUseCase: DeleteEventInteractor
    ) : BundleAwareViewModelFactory<EventListViewModel>(bundle) {
        override fun getViewModel(): EventListViewModel {
            return EventListViewModel(application, router, getEventsUseCase, deleteEventUseCase)
        }
    }
}