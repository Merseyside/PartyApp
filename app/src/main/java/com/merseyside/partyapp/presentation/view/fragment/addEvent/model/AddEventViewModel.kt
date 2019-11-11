package com.merseyside.partyapp.presentation.view.fragment.addEvent.model

import android.os.Bundle
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.entity.Status
import com.merseyside.partyapp.domain.interactor.AddEventInteractor
import com.merseyside.partyapp.domain.interactor.CloseEventInteractor
import com.merseyside.partyapp.domain.interactor.GetEventByIdInteractor
import com.merseyside.partyapp.presentation.base.BaseCalcViewModel
import com.merseyside.partyapp.utils.isNameValid
import kotlinx.coroutines.cancel
import ru.terrakok.cicerone.Router

class AddEventViewModel(
    router: Router,
    private val addEventUseCase: AddEventInteractor,
    private val getEventByIdUseCase: GetEventByIdInteractor,
    private val closeEventUseCase: CloseEventInteractor
) : BaseCalcViewModel(router) {

    val eventName = ObservableField<String>()
    val eventNameErrorText = ObservableField("")

    val notes = ObservableField<String>()
    val notesErrorText = ObservableField("")

    val members = ObservableField<List<String>>()
    val membersErrorText = ObservableField("")

    val eventLiveData = MutableLiveData<Event>()

    private var modeField = MODE_ADD

    val closeEventVisibility = ObservableField(false)
    val addMembersVisibility = ObservableField(true)

    private var event: Event? = null

    init {
        eventName.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (!eventNameErrorText.get().isNullOrEmpty()) {
                    eventNameErrorText.set("")
                }
            }
        })

        notes.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (!notesErrorText.get().isNullOrEmpty()) {
                    notesErrorText.set("")
                }
            }
        })

        members.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (!membersErrorText.get().isNullOrEmpty()) {
                    membersErrorText.set("")
                }
            }
        })
    }

    override fun readFrom(bundle: Bundle) {
        bundle.apply {
            if (containsKey(EVENT_KEY)) {
                initWithEvent(serializer.deserialize(bundle.getString(EVENT_KEY)!!))

                eventName.set(bundle.getString(NAME_KEY))
                notes.set(bundle.getString(NOTES_KEY))
                members.set(serializer.deserialize(bundle.getString(MEMBERS_KEY)!!))
            }
        }
    }

    override fun writeTo(bundle: Bundle) {
        bundle.apply {
            putString(NAME_KEY, eventName.get() ?: "")
            putString(NOTES_KEY, notes.get() ?: "")
            putString(
                MEMBERS_KEY,
                serializer.serialize<List<String>>(members.get() ?: arrayOf(""))
            )

            if (modeField == MODE_EDIT) {
                bundle.putString(EVENT_KEY, serializer.serialize<Event>(event!!))
            }
        }

    }

    fun initWithEventId(id: Long) {

        getEventByIdUseCase.execute(
            params = GetEventByIdInteractor.Params(id),
            onComplete = {
                event = it
                initWithEvent(it)
            },
            onError = {
                showErrorMsg(errorMsgCreator.createErrorMsg(it))
            }
        )
    }

    private fun initWithEvent(event: Event) {
        eventName.set(event.name)
        notes.set(event.notes)

        modeField = MODE_EDIT

        closeEventVisibility.set(modeField == MODE_EDIT && event.status == Status.IN_PROCESS)
        addMembersVisibility.set(event.status == Status.IN_PROCESS)
    }

    override fun dispose() {
        addEventUseCase.cancel()
        getEventByIdUseCase.cancel()
        closeEventUseCase.cancel()
    }

    fun onSaveClick() {
        save()
    }

    private fun save() {
        if (!isNameValid(eventName.get())) {
            eventNameErrorText.set(getString(R.string.event_name_error_msg))
            return
        }

        if (notes.get() != null && notes.get()!!.length > 512) {
            notesErrorText.set(getString(R.string.notes_error_msg))
            return
        }

        if (modeField == MODE_ADD) {
            if (members.get() == null || members.get()!!.size < 2) {
                membersErrorText.set(getString(R.string.members_error))
                return
            }
        }

        addEventUseCase.execute(
            params = AddEventInteractor.Params(
                event?.id,
                eventName.get()!!,
                members.get(),
                notes.get() ?: ""
            ),
            onComplete = {
                eventLiveData.value = it

                goBack()
            },

            onError = { throwable ->
                showErrorMsg(errorMsgCreator.createErrorMsg(throwable))
            }
        )

    }

    fun closeEvent() {
        closeEventUseCase.execute(
            params = CloseEventInteractor.Params(event!!.id),
            onComplete = {
                showMsg(getString(R.string.complete))
            },
            onError = {
                showErrorMsg(errorMsgCreator.createErrorMsg(it))
            }
        )
    }

    companion object {
        private const val MODE_ADD = 0
        private const val MODE_EDIT = 1

        private const val NAME_KEY = "name"
        private const val NOTES_KEY = "notes"
        private const val MEMBERS_KEY = "members"

        private const val EVENT_KEY = "event"

        private const val TAG = "AddEventViewModel"
    }
}