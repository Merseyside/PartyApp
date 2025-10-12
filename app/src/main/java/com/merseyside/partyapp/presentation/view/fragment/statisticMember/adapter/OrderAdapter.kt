package com.merseyside.partyapp.presentation.view.fragment.statisticMember.adapter

import com.merseyside.adapters.SimpleAdapter
import com.merseyside.adapters.core.config.AdapterConfig
import com.merseyside.adapters.core.config.init.initAdapter
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.entity.Order
import com.merseyside.partyapp.presentation.view.fragment.statisticMember.model.OrderItemViewModel

class OrderAdapter(adapterConfig: AdapterConfig<Order, OrderItemViewModel>) : SimpleAdapter<Order, OrderItemViewModel>(adapterConfig) {

    override fun getLayoutIdForViewType(viewType: Int): Int {
        return R.layout.view_order
    }

    override fun getBindingVariable(): Int = BR.obj

    override fun createItemViewModel(item: Order): OrderItemViewModel {
        return OrderItemViewModel(item, CalcApplication.getInstance().prefsHelper.getCurrency())
    }

    companion object {
        operator fun invoke(): OrderAdapter {
            return initAdapter(::OrderAdapter)
        }
    }
}