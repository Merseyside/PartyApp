package com.merseyside.partyapp.presentation.view.fragment.eventList.model

import android.util.Log
import androidx.databinding.ObservableField
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.domain.interactor.AddEventInteractor
import com.merseyside.partyapp.domain.interactor.GetEventsInteractor
import com.merseyside.partyapp.presentation.base.BaseCalcViewModel
import kotlinx.coroutines.cancel
import ru.terrakok.cicerone.Router

class EventListViewModel(
    private val router: Router,
    private val addEventUseCase: AddEventInteractor,
    private val getEventsUseCase: GetEventsInteractor
) : BaseCalcViewModel() {

    val eventsVisibility = ObservableField<Boolean>()

    val eventsContainer = ObservableField<List<Event>>()

    override fun dispose() {
        addEventUseCase.cancel()
        getEventsUseCase.cancel()
    }

    fun showEvents() {
        getEventsUseCase.execute(
            onComplete = {

                Log.d(TAG, "here")

                eventsVisibility.set(it.isNotEmpty())

                eventsContainer.set(it)
            },
            onError = {
                showErrorMsg(errorMsgCreator.createErrorMsg(it))
            }
        )
    }

    companion object {
        private const val TAG = "EventListViewModel"
    }
}