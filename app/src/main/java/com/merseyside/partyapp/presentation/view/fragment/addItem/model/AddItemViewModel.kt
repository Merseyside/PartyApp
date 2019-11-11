package com.merseyside.partyapp.presentation.view.fragment.addItem.model

import android.os.Bundle
import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.partyapp.data.db.item.MemberItemInfo
import com.merseyside.partyapp.domain.interactor.AddItemInteractor
import com.merseyside.partyapp.presentation.base.BaseCalcViewModel
import com.merseyside.partyapp.utils.convertPriceToDouble
import com.merseyside.partyapp.utils.isNameValid
import com.merseyside.partyapp.utils.isPriceValid
import kotlinx.coroutines.cancel
import ru.terrakok.cicerone.Router

class AddItemViewModel(
    router: Router,
    private val addItemUseCase: AddItemInteractor
) : BaseCalcViewModel(router) {

    private lateinit var event: Event
    private var item: Item? = null

    val name = ObservableField<String>()
    val nameErrorText = ObservableField("")

    val price = ObservableField<String>()
    val priceErrorText = ObservableField("")

    val description = ObservableField<String>()

    val membersContainer = ObservableField<List<Member>>()
    val selectableMembers = ObservableField<List<Pair<Member, Boolean>>>()
    val selectableMembersErrorText = ObservableField("")

    val payMember = ObservableField<Member>()

    init {
        name.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (!nameErrorText.get().isNullOrEmpty()) {
                    nameErrorText.set("")
                }
            }
        })

        price.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                val priceStr = price.get()!!

                if (!priceErrorText.get().isNullOrEmpty()) {
                    priceErrorText.set("")
                }

                if (priceStr.contains(".")) {
                    val subs = priceStr.split(".")
                    if (subs.last().length > 2) {
                        price.set(priceStr.dropLast(1))
                    }
                }
            }
        })

        selectableMembers.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (!selectableMembersErrorText.get().isNullOrEmpty()) {
                    selectableMembersErrorText.set("")
                }
            }
        })
    }

    override fun readFrom(bundle: Bundle) {
        bundle.apply {
            event = serializer.deserialize(getString(EVENT_KEY)!!)
            if (containsKey(ITEM_KEY)) item = serializer.deserialize(getString(ITEM_KEY)!!)

            name.set(getString(NAME_KEY)!!)
            description.set(getString(DESCRIPTION_KEY)!!)
            price.set(getString(PRICE_KEY)!!)
            selectableMembers.set(serializer.deserialize(getString(SELECTED_MEMBERS_KEY)!!))
            payMember.set(serializer.deserialize(getString(PAY_MEMBER_KEY)!!))
        }
    }

    override fun writeTo(bundle: Bundle) {
        bundle.apply {
            putString(NAME_KEY, serializer.serialize<Event>(event))
            if (item != null) putString(ITEM_KEY, serializer.serialize<Item>(item!!))

            putString(NAME_KEY, name.get() ?: "")
            putString(DESCRIPTION_KEY, description.get() ?: "")
            putString(PRICE_KEY, price.get() ?: "0")
            putString(NAME_KEY, name.get() ?: "")

            putString(PAY_MEMBER_KEY, serializer.serialize<Member>(payMember.get()!!))
            if (selectableMembers.get() != null) putString(SELECTED_MEMBERS_KEY, serializer.serialize<List<Pair<Member, Boolean>>>(selectableMembers.get()!!))
        }
    }

    fun init(event: Event, item: Item? = null) {
        this.event = event
        this.item = item

        membersContainer.set(event.members)

        if (item != null) {

            name.set(item.name)
            price.set(item.price.toString())
            description.set(item.description)

            selectableMembers.set(event.members.map { member ->
                item.membersInfo.forEach { info ->
                    if (info.id == member.id) {
                        return@map Pair(member, true)
                    }
                }

                return@map Pair(member, false)
            })

            setPayMember(item.payMember)
        } else {
            setPayMember(event.members.first())

            selectableMembers.set(event.members.map { Pair(it, false) })
        }
    }

    override fun dispose() {
        addItemUseCase.cancel()
    }

    private fun saveItem() {
        if (!isNameValid(name.get())) {
            nameErrorText.set(getString(R.string.name_error_msg))
            return
        }

        if (!isPriceValid(price.get())) {
            priceErrorText.set(getString(R.string.price_error_msg))
            return
        }

        if (selectableMembers.get()?.filter { it.second }.isNullOrEmpty()) {
            selectableMembersErrorText.set(getString(R.string.members_error_msg))
            return
        }

        addItemUseCase.execute(
            params = AddItemInteractor.Params(
                id = item?.id,
                eventId = event.id,
                name = name.get()!!,
                description = description.get() ?: "",
                price = convertPriceToDouble(price.get()!!),
                payMember = MemberItemInfo(payMember.get()!!.id, payMember.get()!!.name, 1f),
                membersInfo = selectableMembers.get()!!.mapNotNull {
                    if (it.second) {
                        MemberItemInfo(it.first.id, it.first.name, 1f)
                    } else {
                        null
                    }
                }
            ),
            onComplete = {
                goBack()
            },
            onError = {showErrorMsg(errorMsgCreator.createErrorMsg(it))}
        )
    }

    private fun setPayMember(member: Member) {
        payMember.set(member)
    }

    fun onSaveClick() {
        saveItem()
    }

    companion object {
        private const val TAG = "AddItemViewModel"

        private const val ITEM_KEY = "item"
        private const val EVENT_KEY = "event"

        private const val NAME_KEY = "name"
        private const val PRICE_KEY = "price"
        private const val DESCRIPTION_KEY = "description"
        private const val PAY_MEMBER_KEY = "pay_member"
        private const val SELECTED_MEMBERS_KEY = "selected_members"

    }
}