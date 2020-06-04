package com.merseyside.partyapp.presentation.di.module

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.merseyside.partyapp.domain.interactor.DeleteEventInteractor
import com.merseyside.partyapp.domain.interactor.GetEventsInteractor
import com.merseyside.partyapp.presentation.view.fragment.eventList.model.EventListViewModel
import com.merseyside.merseyLib.presentation.fragment.BaseFragment
import com.merseyside.merseyLib.presentation.model.BundleAwareViewModelFactory
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class EventListModule(
    private val fragment: BaseFragment,
    private val bundle: Bundle?
) {

    @Provides
    internal fun eventListViewModelProvider(
        router: Router,
        getEventsUseCase: GetEventsInteractor,
        deleteEventUseCase: DeleteEventInteractor
    ): ViewModelProvider.Factory {
        return EventListViewModelProviderFactory(bundle, router, getEventsUseCase, deleteEventUseCase)
    }

    @Provides
    internal fun provideEventListViewModel(factory: ViewModelProvider.Factory): EventListViewModel {
        return ViewModelProviders.of(fragment, factory).get(EventListViewModel::class.java)
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
        private val router: Router,
        private val getEventsUseCase: GetEventsInteractor,
        private val deleteEventUseCase: DeleteEventInteractor
    ) : BundleAwareViewModelFactory<EventListViewModel>(bundle) {
        override fun getViewModel(): EventListViewModel {
            return EventListViewModel(router, getEventsUseCase, deleteEventUseCase)
        }
    }
}