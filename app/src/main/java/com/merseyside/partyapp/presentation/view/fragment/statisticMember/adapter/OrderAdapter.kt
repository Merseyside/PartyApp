package com.merseyside.partyapp.presentation.view.fragment.statisticMember.adapter

import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.entity.Order
import com.merseyside.partyapp.presentation.view.fragment.statisticMember.model.OrderItemViewModel
import com.upstream.basemvvmimpl.presentation.adapter.BaseAdapter

class OrderAdapter : BaseAdapter<Order, OrderItemViewModel>() {

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.view_order
    }

    override fun getBindingVariable(): Int {
        return BR.obj
    }

    override fun createItemViewModel(obj: Order): OrderItemViewModel {
        return OrderItemViewModel(obj)
    }
}