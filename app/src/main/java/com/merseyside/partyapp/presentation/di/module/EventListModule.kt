package com.merseyside.partyapp.presentation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.merseyside.partyapp.domain.interactor.DeleteEventInteractor
import com.merseyside.partyapp.domain.interactor.GetEventsInteractor
import com.merseyside.partyapp.presentation.view.fragment.eventList.model.EventListViewModel
import com.upstream.basemvvmimpl.presentation.fragment.BaseFragment
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class EventListModule(private val fragment: BaseFragment) {

    @Provides
    internal fun eventListViewModelProvider(
        router: Router,
        getEventsUseCase: GetEventsInteractor,
        deleteEventUseCase: DeleteEventInteractor
    ): ViewModelProvider.Factory {
        return EventListViewModelProviderFactory(router, getEventsUseCase, deleteEventUseCase)
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
        private val router: Router,
        private val getEventsUseCase: GetEventsInteractor,
        private val deleteEventUseCase: DeleteEventInteractor
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == EventListViewModel::class.java) {
                return EventListViewModel(router, getEventsUseCase, deleteEventUseCase) as T
            }
            throw IllegalArgumentException("Unknown class title")
        }
    }
}