package com.merseyside.partyapp.presentation.view.fragment.statisticMember.model

import android.content.Context
import androidx.annotation.AttrRes
import androidx.databinding.Bindable
import com.merseyside.adapters.core.model.AdapterViewModel
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.entity.Order
import com.merseyside.partyapp.utils.doubleToStringPrice
import com.merseyside.archy.presentation.interfaces.IStringHelper
import com.google.android.material.R.attr as MaterialAttr


class OrderItemViewModel(
    item: Order,
    private val currency: String
) : AdapterViewModel<Order>(item), IStringHelper {

    @Bindable
    fun getTitle(): String {
        return item.title
    }

    @Bindable
    fun getAnotherMember(): String {
        return when (item) {
            is Order.OrderOwner -> {
                getString(R.string.for_member, item.member.name)
            }

            else -> {
                getString(R.string.from_member, item.member.name)
            }
        }
    }

    @Bindable
    fun getPrice(): String {
        return getString(R.string.price, doubleToStringPrice(item.price), currency)
    }

    @Bindable
    @AttrRes
    fun getBackgroundColor(): Int {
        return when (item) {
            is Order.OrderOwner -> {
                if (item.ownerId == item.member.id) {
                    com.google.android.material.R.attr.colorOnBackground
                } else {
                    android.R.attr.colorPrimary
                }
            }
            else -> {
                android.R.attr.colorError
            }
        }
    }

    @Bindable
    @AttrRes
    fun getTextColor(): Int {
        return when (item) {
            is Order.OrderOwner -> {
                if (item.ownerId == item.member.id) {
                    R.attr.calcTextColor
                } else {
                    MaterialAttr.colorOnPrimary
                }
            }

            is Order.OrderReceiver -> {
                MaterialAttr.colorOnError
            }
        }
    }

    override fun getLocaleContext(): Context {
        return CalcApplication.getInstance()
    }
}