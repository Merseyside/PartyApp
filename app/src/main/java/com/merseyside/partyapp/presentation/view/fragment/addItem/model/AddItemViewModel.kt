package com.merseyside.partyapp.presentation.view.fragment.addItem.model

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.partyapp.data.db.item.MemberInfo
import com.merseyside.partyapp.domain.interactor.AddItemInteractor
import com.merseyside.partyapp.presentation.base.BaseCalcViewModel
import com.merseyside.partyapp.utils.*
import com.merseyside.mvvmcleanarch.data.deserialize
import com.merseyside.mvvmcleanarch.data.serialize
import kotlinx.coroutines.cancel
import kotlinx.serialization.ImplicitReflectionSerializer
import ru.terrakok.cicerone.Router

class AddItemViewModel(
    router: Router,
    private val addItemUseCase: AddItemInteractor
) : BaseCalcViewModel(router) {

    private lateinit var event: Event
    private var item: Item? = null

    val name = ObservableField<String>()
    val nameErrorText = ObservableField("")
    val itemNameHint = ObservableField<String>()

    val price = ObservableField<String>()
    val priceErrorText = ObservableField("")
    val priceHint = ObservableField<String>()

    val description = ObservableField<String>()
    val itemDescriptionHint = ObservableField<String>()

    val percent = ObservableField<String>()
    val percentHint = ObservableField<String>()

    val membersContainer = ObservableField<List<Member>>()
    val selectableMembers = ObservableField<List<Pair<MemberInfo, Boolean>>>()
    val selectableMembersErrorText = ObservableField("")

    val spinnerSelectedMembers = ObservableField<List<MemberInfo>>()
    val spinnerSelectedMember = ObservableField<MemberInfo>()

    val payMember = ObservableField<Member>()
    val whoPaysTitle = ObservableField<String>()
    val forWhomTitle = ObservableField<String>()
    val additionalSettingsTitle = ObservableField<String>()
    val percentSettingsTitle = ObservableField<String>()
    val save = ObservableField<String>()

    override fun updateLanguage(context: Context) {
        super.updateLanguage(context)

        itemNameHint.set(context.getString(R.string.item_title))
        itemDescriptionHint.set(context.getString(R.string.item_description))
        priceHint.set(context.getString(R.string.item_total_price))

        whoPaysTitle.set(context.getString(R.string.choose_pays_member))
        forWhomTitle.set(context.getString(R.string.choose_members))

        additionalSettingsTitle.set(context.getString(R.string.additional_settings))
        percentSettingsTitle.set(context.getString(R.string.percent_setting))
        save.set(context.getString(R.string.save))
    }

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

                if (selectableMembers.get()?.isNotEmpty() == true) {
                    spinnerSelectedMembers.set(selectableMembers.get()!!.mapNotNull {
                        if (it.second) {
                            it.first
                        } else {
                            null
                        }
                    })

                    percentHint.set(getHumanReadablePercents(calculatePercentHint()))
                } else {
                    spinnerSelectedMembers.set(null)
                }
            }
        })

        spinnerSelectedMember.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                spinnerSelectedMember.get()?.let {
                    percent.set(getHumanReadablePercents(it.percent))
                }
            }
        })

        percent.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (spinnerSelectedMember.get() != null) {
                    var percentStr = percent.get()

                    if (percentStr != null) {

                        if (percentStr.isEmpty()) {
                            percentStr = "0"
                        }

                        if (!isPercentValid(percentStr)) {
                            if (getHumanReadablePercents(spinnerSelectedMember.get()!!.percent) != percentStr) {
                                percent.set(getHumanReadablePercents(spinnerSelectedMember.get()!!.percent))
                            }
                        } else {
                            if (convertPercentToInt(percentStr) == 0) {
                                percent.set("")
                            }
                            spinnerSelectedMember.get()!!.percent =
                                getInternalPercents(percentStr)
                        }

                        percentHint.set(getHumanReadablePercents(calculatePercentHint()))
                    }
                }
            }
        })
    }

    private fun calculatePercentHint(): Float {
        if (spinnerSelectedMembers.get()?.isNotEmpty() == true) {
            var hundred = 1f
            var equalCount = 0

            spinnerSelectedMembers.get()?.forEach {
                if (it.percent != 0f) {
                    hundred -= it.percent
                } else {
                    equalCount++
                }
            }

            return hundred / equalCount
        } else {
            return 0f
        }
    }

    @UseExperimental(ImplicitReflectionSerializer::class)
    override fun readFrom(bundle: Bundle) {
        bundle.apply {
            event = getString(EVENT_KEY)!!.deserialize()
            if (containsKey(ITEM_KEY)) item = getString(ITEM_KEY)!!.deserialize()

            membersContainer.set(event.members)
            name.set(getString(NAME_KEY)!!)
            description.set(getString(DESCRIPTION_KEY)!!)
            price.set(getString(PRICE_KEY)!!)

            selectableMembers.set(getString(SELECTED_MEMBERS_KEY)!!.deserialize(kSerializer))
            payMember.set(getString(PAY_MEMBER_KEY)!!.deserialize())
        }
    }

    @UseExperimental(ImplicitReflectionSerializer::class)
    override fun writeTo(bundle: Bundle) {
        bundle.apply {
            putString(EVENT_KEY, event.serialize())
            if (item != null) putString(ITEM_KEY, item!!.serialize())


            putString(NAME_KEY, name.get() ?: "")
            putString(DESCRIPTION_KEY, description.get() ?: "")
            putString(PRICE_KEY, price.get() ?: "0")
            putString(NAME_KEY, name.get() ?: "")

            putString(PAY_MEMBER_KEY, (payMember.get() ?: event.members.first()).serialize())

            if (selectableMembers.get() != null) {
                putString(SELECTED_MEMBERS_KEY, selectableMembers.get()!!.serialize(kSerializer))
            }
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
                        return@map Pair(info, true)
                    }
                }

                return@map Pair(MemberInfo(member.id, member.name, 0f), false)
            })

            Handler(Looper.getMainLooper()).postDelayed({
                setPayMember(item.payMember)
            }, 100)

        } else {
            setPayMember()

            selectableMembers.set(event.members.map { Pair(MemberInfo(it.id, it.name, 0f), false) })
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
                payMember = Member(payMember.get()!!.id, payMember.get()!!.name),
                membersInfo = spinnerSelectedMembers.get()!!
            ),
            onComplete = {
                goBack()
            },
            onError = {showErrorMsg(errorMsgCreator.createErrorMsg(it))}
        )
    }

    private fun setPayMember(member: Member? = null) {
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