package com.merseyside.partyapp.presentation.view.fragment.addEvent.model

import android.util.Log
import androidx.databinding.ObservableField
import com.merseyside.partyapp.R
import com.merseyside.partyapp.domain.interactor.AddEventInteractor
import com.merseyside.partyapp.presentation.base.BaseCalcViewModel
import kotlinx.coroutines.cancel
import ru.terrakok.cicerone.Router

class AddEventViewModel(
    private val router: Router,
    private val addEventUseCase: AddEventInteractor
) : BaseCalcViewModel(router) {

    val eventNameObservable = ObservableField<String>()
    val notesObservable = ObservableField<String>()
    val membersObservable = ObservableField<List<String>>()

    override fun dispose() {
        addEventUseCase.cancel()
    }

    fun onSaveClicked() {
        Log.d(TAG, "${eventNameObservable.get()}")

        if (eventNameObservable.get().isNullOrEmpty()) {
            showErrorMsg(getString(R.string.empty_title_error))
            return
        }

        if (membersObservable.get() == null || membersObservable.get()!!.size < 2) {
            showErrorMsg(getString(R.string.members_error))
            return
        }

        addEventUseCase.execute(
            params = AddEventInteractor.Params(
                eventNameObservable.get()!!,
                membersObservable.get()!!,
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