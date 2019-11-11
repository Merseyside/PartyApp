package com.merseyside.partyapp.presentation.view.fragment.statisticMember.model

import androidx.annotation.AttrRes
import androidx.databinding.Bindable
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.entity.Order
import com.merseyside.partyapp.utils.doubleToStringPrice
import com.upstream.basemvvmimpl.presentation.model.BaseAdapterViewModel


class OrderItemViewModel(override var obj: Order) : BaseAdapterViewModel<Order>(obj) {

    override fun areItemsTheSame(obj: Order): Boolean {
        return this.obj == obj
    }

    override fun notifyUpdate() {}

    @Bindable
    fun getTitle(): String {
        return obj.title
    }

    @Bindable
    fun getAnotherMember(): String {
        return when (obj) {
            is Order.OrderOwner -> {
                CalcApplication.getInstance().getActualString(R.string.for_member, obj.member.name)
            }

            else -> {
                CalcApplication.getInstance().getActualString(R.string.from_member, obj.member.name)
            }
        }
    }

    @Bindable
    fun getPrice(): String {
        return CalcApplication.getInstance().getActualString(R.string.price, doubleToStringPrice(obj.price))
    }

    @Bindable
    @AttrRes
    fun getBackgroundColor(): Int {
        return when (obj) {
            is Order.OrderOwner -> {
                if (obj.ownerId == obj.member.id) {
                    R.attr.colorOnBackground
                } else {
                    R.attr.colorPrimary
                }
            }
            else -> {
                R.attr.colorError
            }
        }
    }

    @Bindable
    @AttrRes
    fun getTextColor(): Int {
        return when (obj) {
            is Order.OrderOwner -> {
                if (obj.ownerId == obj.member.id) {
                    R.attr.calcTextColor
                } else {
                    R.attr.colorOnPrimary
                }
            }

            is Order.OrderReceiver -> {
                R.attr.colorOnError
            }
        }
    }



}