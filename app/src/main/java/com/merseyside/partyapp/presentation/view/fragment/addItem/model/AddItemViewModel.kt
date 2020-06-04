package com.merseyside.partyapp.presentation.view.fragment.addItem.model

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.merseyside.merseyLib.utils.ext.isZero
import com.merseyside.merseyLib.utils.randomBool
import com.merseyside.merseyLib.utils.serialization.deserialize
import com.merseyside.merseyLib.utils.serialization.serialize
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.partyapp.data.db.item.MemberInfo
import com.merseyside.partyapp.domain.interactor.AddItemInteractor
import com.merseyside.partyapp.presentation.base.BaseCalcViewModel
import com.merseyside.partyapp.utils.*
import kotlinx.coroutines.cancel
import kotlinx.serialization.builtins.ListSerializer
import ru.terrakok.cicerone.Router

class AddItemViewModel(
    router: Router,
    private val prefsHelper: PrefsHelper,
    private val addItemUseCase: AddItemInteractor
) : BaseCalcViewModel(router) {

    private lateinit var event: Event
    private var item: Item? = null

    val name = ObservableField<String>()
    val nameErrorText = ObservableField("")

    val price = ObservableField<String>("")
    val priceErrorText = ObservableField("")
    val isPriceValid = ObservableField<Boolean>(true)
    val priceHint = ObservableField<String>(getString(R.string.item_total_price))

    val description = ObservableField<String>()

    val percent = ObservableField<String>()
    val percentHint = ObservableField<String>()
    val memberPrice = ObservableField<String>()
    val memberPriceHint = ObservableField<String>()
    private var memberPriceValue: String? = null

    val membersContainer = ObservableField<List<Member>>()
    val selectableMembers = ObservableField<List<MemberInfo>>()
    val selectedMembers = ObservableField<List<MemberInfo>>()
    val selectableMembersErrorText = ObservableField("")

    val spinnerSelectedMembers = ObservableField<List<MemberInfo>>()
    val spinnerSelectedMember = ObservableField<MemberInfo>()

    val payMember = ObservableField<Member>()
    val additionalSettingsError = ObservableField<String>(getString(R.string.fill_price_error))
    val currency = ObservableField<String>()

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
                val priceStr = price.get()

                if (!priceStr.isNullOrEmpty()) {

                    isPriceValid.set(isPriceValid(priceStr))

                    if (!priceErrorText.get().isNullOrEmpty()) {
                        priceErrorText.set("")
                    }

                    try {
                        val doublePrice = convertPriceToDouble(priceStr)
                        if (percent.get().isNullOrEmpty()) {
                            val percent = convertPercentToFloat(percentHint.get() ?: "0")

                            memberPriceHint.set(doubleToStringPrice(convertPercentToPrice(percent, doublePrice)))
                        } else {
                            val percent = convertPercentToFloat(percent.get()!!)

                            memberPrice.set(doubleToStringPrice(convertPercentToPrice(percent, doublePrice)))
                        }

                        additionalSettingsError.set("")

                    } catch (e: NumberFormatException) {
                        additionalSettingsError.set(getString(R.string.fill_price_error))
                    }

                    val calculatedPrice: String?

                    if (priceStr.endsWith("\n") && priceStr.length > 1) {
                        val formattedString = priceStr.dropLast(1)

                        if (formattedString.last().isDigit()) {
                            try {
                                val result = calculate(formattedString)
                                if (result != null) {
                                    price.set(result)
                                    return
                                } else {
                                    try {
                                        price.set(doubleToStringPrice(formattedString.toDouble()))
                                    } catch (e: NumberFormatException) {}
                                }
                            } catch (e: NumberFormatException) {
                                priceErrorText.set(getString(R.string.price_error_msg))
                            }
                        } else {
                            price.set(formattedString.dropLast(1)+"\n")
                            return
                        }

                        price.set(formattedString)
                    } else {

                        try {
                            calculatedPrice = checkPriceForCalculation(priceStr)
                        } catch (e: NumberFormatException) {
                            priceErrorText.set(getString(R.string.wrong_format))
                            return
                        } catch (e: IllegalStateException) {
                            price.set(priceStr.dropLast(1))
                            return
                        }

                        if (calculatedPrice != null) {
                            price.set(calculatedPrice)
                        } else {

                            if (!priceStr.last().isDigit()) {

                                if (priceStr.length > 1 && !priceStr.substring(priceStr.length - 2).toCharArray()[0].isDigit()) {
                                    price.set(priceStr.dropLast(1))
                                }
                            }
                        }
                    }
                } else {
                    priceErrorText.set(getString(R.string.wrong_format))
                    additionalSettingsError.set(getString(R.string.fill_price_error))
                }
            }
        })

        selectedMembers.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (!selectableMembersErrorText.get().isNullOrEmpty()) {
                    selectableMembersErrorText.set("")
                }

                if (selectedMembers.get()?.isNotEmpty() == true) {
                    spinnerSelectedMembers.set(selectedMembers.get()!!)

                    val calculatedPercent = calculatePercentHint()


                    percentHint.set(getHumanReadablePercents(calculatedPercent))


                    if (isPriceValid(price.get())) {
                        memberPriceHint.set(doubleToStringPrice(convertPercentToPrice(calculatedPercent, convertPriceToDouble(price.get()!!))))
                    }
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
                    val percentStr = percent.get()
                    val totalPrice = price.get()

                    if (percentStr != null) {

                        if (percentStr == "0") {
                            percent.set("")
                            return
                        }

                        if (percentStr == ".") {
                            percent.set("0.")
                            return
                        }

                        if (!isPercentValid(percentStr)) {
                            if (percentStr.isEmpty()) {
                                spinnerSelectedMember.get()!!.percent = 0f
                                memberPrice.set("")
                            } else {

                                val humanReadablePercent =
                                    getHumanReadablePercents(spinnerSelectedMember.get()!!.percent)
                                if (humanReadablePercent != percentStr) {
                                    percent.set(humanReadablePercent)
                                }
                            }
                        } else {
                            if (isPriceValid(totalPrice)) {

                                spinnerSelectedMember.get()!!.percent = convertPercentToFloat(percentStr)

                                if (memberPrice.get().isNullOrEmpty() || isPercentsAreDifferent(convertPercentToFloat(percentStr), convertPriceToPercent(
                                        convertPriceToDouble(memberPrice.get()!!), convertPriceToDouble(totalPrice!!)))) {
                                    memberPrice.set(
                                        doubleToStringPrice(
                                            convertPercentToPrice(
                                                spinnerSelectedMember.get()!!.percent,
                                                convertPriceToDouble(totalPrice!!)
                                            )
                                        )
                                    )
                                }
                                return
                            }
                        }

                        val percent = calculatePercentHint()
                        percentHint.set(getHumanReadablePercents(percent))

                        if (isPriceValid(totalPrice)) {
                            val pr = doubleToStringPrice(
                                convertPercentToPrice(
                                    percent,
                                    convertPriceToDouble(price.get()!!)
                                )
                            )
                            memberPriceHint.set(pr)
                        }
                    }
                }
            }
        })

        memberPrice.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {

                if (spinnerSelectedMember.get() != null) {

                    val memberPriceStr = memberPrice.get()

                    var percentString = percent.get()
                    val totalPrice = price.get()

                    if (percentString.isNullOrEmpty()) {
                        percentString = "0"
                    }

                    if (!isPriceValid(memberPriceStr)) {

                        if (memberPriceStr.isNullOrEmpty()) {
                            if (percentString.isNotEmpty()) {
                                percent.set("")
                            }

                            return
                        }

                    } else {
                        if (convertPriceToDoubleWithFormat(memberPriceStr!!) != convertPriceToDouble(memberPriceStr)) {
                            memberPrice.set(doubleToStringPrice(convertPriceToDouble(memberPriceStr)))
                            return
                        }

                        if (!isMemberPriceValid(
                                memberPriceStr,
                                convertPriceToDouble(totalPrice!!)
                            )
                        ) {
                            if (memberPriceValue != null) {
                                memberPrice.set(memberPriceValue)
                            } else {
                                memberPrice.set(
                                    doubleToStringPrice(
                                        convertPercentToPrice(
                                            convertPercentToFloat(percentString),
                                            convertPriceToDouble(totalPrice)
                                        )
                                    )
                                )
                            }
                        } else {

                            if (isPriceValid(totalPrice)) {
                                try {

                                    memberPriceValue = memberPriceStr

                                    val calculatedPercent = convertPriceToPercent(
                                        convertPriceToDouble(memberPriceStr),
                                        convertPriceToDouble(totalPrice)
                                    )

                                    var humanReadablePercent =
                                        getHumanReadablePercents(calculatedPercent)

                                    if (humanReadablePercent == "0") humanReadablePercent = ""

                                    if (isPercentsAreDifferent(
                                            calculatedPercent, convertPriceToPercent(
                                                convertPriceToDouble(percentString),
                                                convertPriceToDouble(totalPrice)
                                            )
                                        )
                                    ) {
                                        percent.set(humanReadablePercent)
                                    }
                                } catch (e: IllegalArgumentException) {
                                    e.printStackTrace()

                                    val validValue = doubleToStringPrice(
                                        convertPercentToPrice(
                                            convertPercentToFloat(percentString),
                                            convertPriceToDouble(totalPrice)
                                        )
                                    )
                                    if (validValue != memberPriceStr) {
                                        memberPrice.set(validValue)
                                    }
                                }
                            }
                        }
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

            return if (equalCount == 0) {
                hundred
            } else {
                hundred / equalCount
            }
        } else {
            return 0f
        }
    }

    override fun readFrom(bundle: Bundle) {
        bundle.apply {
            event = getString(EVENT_KEY)!!.deserialize()
            if (containsKey(ITEM_KEY)) item = getString(ITEM_KEY)!!.deserialize()

            membersContainer.set(event.members)
            name.set(getString(NAME_KEY)!!)
            description.set(getString(DESCRIPTION_KEY)!!)
            price.set(getString(PRICE_KEY)!!)

            selectableMembers.set(getString(SELECTED_MEMBERS_KEY)!!.deserialize(ListSerializer(MemberInfo.serializer())))
            payMember.set(getString(PAY_MEMBER_KEY)!!.deserialize())
        }
    }

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
                putString(SELECTED_MEMBERS_KEY, selectableMembers.get()!!.serialize(ListSerializer(MemberInfo.serializer())))
            }
        }
    }

    fun init(event: Event, item: Item? = null) {
        this.event = event
        this.item = item

        currency.set(prefsHelper.getCurrency())
        membersContainer.set(event.members)

        if (item != null) {

            name.set(item.name)
            price.set(doubleToStringPrice(item.price))
            description.set(item.description)

            selectableMembers.set(event.members.map { member ->
                item.membersInfo.forEach { info ->
                    if (info.id == member.id) {
                        return@map info
                    }
                }

                return@map MemberInfo(member.id, member.name, member.avatarUrl, member.phone, 0f)
            })

            Handler(Looper.getMainLooper()).postDelayed({selectedMembers.set(item.membersInfo) }, 50)

            Handler(Looper.getMainLooper()).postDelayed({
                setPayMember(item.payMember)
            }, 100)

        } else {
            setPayMember()

            selectableMembers.set(event.members.map { MemberInfo(it.id, it.name, it.avatarUrl, it.phone, 0f)}.also { Log.d(TAG, it.toString()) })
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

        Log.d(TAG, "${spinnerSelectedMembers.get()}")
        if (spinnerSelectedMembers.get() == null || spinnerSelectedMembers.get()!!.isEmpty()) {
            selectableMembersErrorText.set(getString(R.string.members_error_msg))
            return
        }

        var totalPercent = 0f
        var containsZeroPercentMember = false
        selectableMembers.get()!!.forEach {
                if (it.percent.isZero()) {
                    containsZeroPercentMember = true
                } else {
                    totalPercent += it.percent
                }
            }

        if (containsZeroPercentMember) {
            if (totalPercent > 1f) {
                showErrorMsg(getString(R.string.members_price_too_much_error), getString(R.string.reset)) { resetMembersPercents() }
                return
            }
        } else {
            if (totalPercent < 0.95f) {
                showErrorMsg(getString(R.string.members_price_too_few_error), getString(R.string.reset)) { resetMembersPercents() }
                return
            } else if (totalPercent > 1f) {
                showErrorMsg(getString(R.string.members_price_too_much_error), getString(R.string.reset)) { resetMembersPercents() }
                return
            }
        }

        addItemUseCase.execute(
            params = AddItemInteractor.Params(
                id = item?.id,
                eventId = event.id,
                name = name.get()!!,
                description = description.get() ?: "",
                price = convertPriceToDouble(price.get()!!),
                payMember = payMember.get()?.let {Member(it.id, it.name, it.avatarUrl, it.phone) } ?: throw IllegalStateException("Pay member is null!"),
                membersInfo = spinnerSelectedMembers.get()!!
            ),
            onComplete = {
                logItemEvent(item?.id)

                if (randomBool(0.25f)) showInterstitial()

                goBack()
            },
            onError = {showErrorMsg(errorMsgCreator.createErrorMsg(it))}
        )
    }

    private fun resetMembersPercents() {
        selectableMembers.get()?.forEach {
            it.percent = 0f
        }

        if (price.get()?.isNotEmpty() == true) {
            percent.set("")
            memberPrice.set("")
        }
    }

    private fun setPayMember(member: Member? = null) {
        payMember.set(member)
    }

    private fun logItemEvent(id: Long?) {
        val eventName = if (id != null) {
            "edit_item"
        } else {
            "add_item"
        }

        logEvent(eventName, Bundle().apply {
            putString("event_name", event.name)
        })
    }

    fun onSaveClick() {
        saveItem()
    }

    fun onSelectAllClick() {
        selectedMembers.set(selectableMembers.get())
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