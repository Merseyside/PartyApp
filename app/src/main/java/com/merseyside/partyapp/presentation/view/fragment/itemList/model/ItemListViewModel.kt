package com.merseyside.partyapp.presentation.view.fragment.itemList.model

import android.util.Log
import androidx.databinding.ObservableField
import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.partyapp.domain.interactor.DeleteItemInteractor
import com.merseyside.partyapp.domain.interactor.GetItemsByEventIdInteractor
import com.merseyside.partyapp.presentation.base.BaseCalcViewModel
import com.merseyside.partyapp.presentation.navigation.Screens
import kotlinx.coroutines.cancel
import ru.terrakok.cicerone.Router

class ItemListViewModel(
    private val router: Router,
    private val getItemsByEventIdUseCase: GetItemsByEventIdInteractor,
    private val deleteItemUseCase: DeleteItemInteractor

) : BaseCalcViewModel(router) {

    private var eventId: Long? = null

    val itemsContainer = ObservableField<List<Item>>()

    val itemsVisibility = ObservableField<Boolean>()

    fun init(eventId: Long) {
        this.eventId = eventId

        getItemsById(eventId)
    }

    override fun dispose() {
        getItemsByEventIdUseCase.cancel()
    }

    fun navigateToEditScreen(id: Long) {
        router.navigateTo(Screens.EditEventScreen(id))
    }

    fun navigateToEditItemScreen() {
        router.navigateTo(Screens.EditItemScreen())
    }

    fun onAddButtonClick() {
        router.navigateTo(Screens.AddItemScreen())
    }

    private fun getItemsById(id: Long) {
        getItemsByEventIdUseCase.execute(
            params = GetItemsByEventIdInteractor.Params(id),
            onComplete = {
                itemsVisibility.set(it.isNotEmpty())

                Log.d(TAG, it.toString())

                itemsContainer.set(it)
            },
            onError = {
                showErrorMsg(errorMsgCreator.createErrorMsg(it))
            }
        )
    }

    fun deleteItem(id: Long) {
        deleteItemUseCase.execute(
            params = DeleteItemInteractor.Params(id),
            onComplete = {
                getItemsById(eventId!!)
            },
            onError = {
                showErrorMsg(errorMsgCreator.createErrorMsg(it))
            }
        )
    }

    companion object {
        private const val TAG = "ItemListViewModel"
    }
}