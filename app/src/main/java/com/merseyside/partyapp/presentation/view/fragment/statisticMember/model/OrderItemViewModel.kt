package com.merseyside.partyapp.presentation.view.fragment.statisticMember.model

import androidx.annotation.DrawableRes
import androidx.databinding.Bindable
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.entity.Order
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
        return  CalcApplication.getInstance().getActualString(R.string.price, obj.price.toString())
    }

    @Bindable
    @DrawableRes
    fun getBackgroundDrawable(): Int {
        return when (obj) {
            is Order.OrderOwner -> {
                R.drawable.order_owner_background
            }

            else -> {
                R.drawable.order_receiver_background
            }
        }
    }



}