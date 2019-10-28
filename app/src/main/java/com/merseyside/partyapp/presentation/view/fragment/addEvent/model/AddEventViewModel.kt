package com.merseyside.partyapp.presentation.view.fragment.addEvent.model

import android.util.Log
import androidx.databinding.ObservableField
import com.merseyside.partyapp.R
import com.merseyside.partyapp.domain.interactor.AddEventInteractor
import com.merseyside.partyapp.domain.interactor.GetEventByIdInteractor
import com.merseyside.partyapp.presentation.base.BaseCalcViewModel
import kotlinx.coroutines.cancel
import ru.terrakok.cicerone.Router

class AddEventViewModel(
    private val router: Router,
    private val addEventUseCase: AddEventInteractor,
    private val getEventByIdUseCase: GetEventByIdInteractor
) : BaseCalcViewModel(router) {

    val eventNameObservable = ObservableField<String>()
    val notesObservable = ObservableField<String>()
    val membersObservable = ObservableField<List<String>>()

    private var id: Long? = null

    fun initWithEventId(id: Long) {
        this.id = id

        getEventByIdUseCase.execute(
            params = GetEventByIdInteractor.Params(id),
            onComplete = {
                eventNameObservable.set(it.name)
                notesObservable.set(it.notes)
            },
            onError = {
                showErrorMsg(errorMsgCreator.createErrorMsg(it))
            }
        )
    }

    override fun dispose() {
        addEventUseCase.cancel()
        getEventByIdUseCase.cancel()
    }

    fun onSaveClick() {
        Log.d(TAG, "${eventNameObservable.get()}")

        if (eventNameObservable.get().isNullOrEmpty()) {
            showErrorMsg(getString(R.string.empty_title_error))
            return
        }

        if (id == null) {
            if (membersObservable.get() == null || membersObservable.get()!!.size < 2) {
                showErrorMsg(getString(R.string.members_error))
                return
            }
        }

        addEventUseCase.execute(
            params = AddEventInteractor.Params(
                id,
                eventNameObservable.get()!!,
                membersObservable.get(),
                notesObservable.get() ?: ""
            ),
            onComplete = {
                goBack()
            },

            onError = { throwable ->
                showErrorMsg(errorMsgCreator.createErrorMsg(throwable))
            }
        )

        Log.d(TAG, membersObservable.get().toString())
    }

    companion object {
        private const val TAG = "AddEventViewModel"
    }
}