package com.merseyside.partyapp.presentation.view.fragment.eventList.model

import android.app.Application
import android.os.Bundle
import androidx.databinding.ObservableField
import com.github.terrakok.cicerone.Router
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.domain.interactor.DeleteEventInteractor
import com.merseyside.partyapp.domain.interactor.GetEventsInteractor
import com.merseyside.partyapp.presentation.base.BaseCalcViewModel
import com.merseyside.partyapp.presentation.navigation.Screens
import kotlinx.coroutines.cancel

class EventListViewModel(
    application: Application,
    private val router: Router,
    private val getEventsUseCase: GetEventsInteractor,
    private val deleteEventUseCase: DeleteEventInteractor
) : BaseCalcViewModel(application, router) {

    val eventsVisibility = ObservableField(true)
    val eventsContainer = ObservableField<List<Event>>()

    override fun readFrom(bundle: Bundle) {}

    override fun writeTo(bundle: Bundle) {}

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

    fun onEventClick() {
        router.navigateTo(Screens.ItemListScreen())
    }

    fun onEditClick(id: Long) {
        router.navigateTo(Screens.EditEventScreen(id))
    }

    fun onDeleteClick(event: Event) {
        showAlertDialog(
            title = getString(R.string.delete_event_title, event.name),
            message = getString(R.string.delete_event_message),
            positiveButtonText = getString(R.string.delete),
            negativeButtonText = getString(R.string.cancel),
            onPositiveClick = {
                deleteEventUseCase.execute(
                    params = DeleteEventInteractor.Params(id = event.id),
                    onComplete = { showEvents() },
                    onError = {
                        showErrorMsg(errorMsgCreator.createErrorMsg(it))
                    }
                )
            }
        )
    }

    fun navigateToSettings() {
        router.navigateTo(Screens.SettingsScreen())
    }

    fun navigateToStatistic() {
        router.navigateTo(Screens.StatisticScreen())
    }
}