package com.merseyside.partyapp.presentation.view.fragment.eventList.model

import androidx.databinding.ObservableField
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.domain.interactor.GetEventsInteractor
import com.merseyside.partyapp.presentation.base.BaseCalcViewModel
import com.merseyside.partyapp.presentation.navigation.Screens
import kotlinx.coroutines.cancel
import ru.terrakok.cicerone.Router

class EventListViewModel(
    private val router: Router,
    private val getEventsUseCase: GetEventsInteractor
) : BaseCalcViewModel(router) {

    val eventsVisibility = ObservableField<Boolean>()

    val eventsContainer = ObservableField<List<Event>>()

    override fun dispose() {
        getEventsUseCase.cancel()
    }

    fun showEvents() {
        getEventsUseCase.execute(
            onComplete = {
                eventsVisibility.set(it.isNotEmpty())

                eventsContainer.set(it)
            },
            onError = {
                showErrorMsg(errorMsgCreator.createErrorMsg(it))
            }
        )
    }

    fun onAddButtonClick() {
        router.navigateTo(Screens.AddEventScreen())
    }

    companion object {
        private const val TAG = "EventListViewModel"
    }
}