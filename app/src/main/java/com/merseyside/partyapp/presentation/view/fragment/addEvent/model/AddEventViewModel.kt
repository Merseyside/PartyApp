package com.merseyside.partyapp.presentation.view.fragment.addEvent.model

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.merseyside.mvvmcleanarch.data.deserialize
import com.merseyside.mvvmcleanarch.data.serialize
import kotlinx.coroutines.cancel
import kotlinx.serialization.internal.StringSerializer
import kotlinx.serialization.list
import ru.terrakok.cicerone.Router

class AddEventViewModel(
    router: Router,
    private val addEventUseCase: AddEventInteractor,
    private val getEventByIdUseCase: GetEventByIdInteractor,
    private val closeEventUseCase: CloseEventInteractor
) : BaseCalcViewModel(router) {

    val eventName = ObservableField<String>()
    val eventNameErrorText = ObservableField("")
    val eventNameHint = ObservableField<String>()

    val notes = ObservableField<String>()
    val notesErrorText = ObservableField("")
    val notesHint = ObservableField<String>("")

    val members = ObservableField<List<String>>()
    val membersErrorText = ObservableField("")

    val addItemsTitle = ObservableField<String>()
    val useCommaHint = ObservableField<String>()
    val save = ObservableField<String>()
    val closeEvent = ObservableField<String>()

    val eventLiveData = MutableLiveData<Event>()

    private var modeField = MODE_ADD

    val closeEventVisibility = ObservableField(false)
    val addMembersVisibility = ObservableField(true)

    private var event: Event? = null

    override fun updateLanguage(context: Context) {
        super.updateLanguage(context)

        eventNameHint.set(context.getString(R.string.event_title))
        notesHint.set(context.getString(R.string.notes_hint))
        addItemsTitle.set(context.getString(R.string.add_members))
        useCommaHint.set(context.getString(R.string.use_comma))
        save.set(context.getString(R.string.save))
        closeEvent.set(context.getString(R.string.close_event))
    }

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
                initWithEvent(getString(EVENT_KEY)!!.deserialize())
            } else {

                eventName.set(bundle.getString(NAME_KEY))
                notes.set(bundle.getString(NOTES_KEY))
                members.set(getString(MEMBERS_KEY)!!.deserialize(StringSerializer.list))
            }
        }
    }

    override fun writeTo(bundle: Bundle) {
        bundle.apply {
            putString(NAME_KEY, eventName.get() ?: "")
            putString(NOTES_KEY, notes.get() ?: "")
            putString(
                MEMBERS_KEY,
                members.get()?.serialize(StringSerializer.list) ?: ""
            )

            if (modeField == MODE_EDIT) {
                bundle.putString(EVENT_KEY, event!!.serialize())
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
                logEventChanges(it)
                showInterstitial()

                eventLiveData.value = it
                goBack()
            },

            onError = { throwable ->
                showErrorMsg(errorMsgCreator.createErrorMsg(throwable))
            }
        )

    }

    private fun logEventChanges(event: Event) {
        val eventName = if (modeField == MODE_ADD) {
            "add_event"
        } else {
            "edit_event"
        }

        logEvent(eventName, Bundle().apply {
            putString("event_name", event.name)
            putInt("member_count", event.members.size)
            putString("event_notes", event.notes)
        })
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