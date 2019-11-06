package com.merseyside.partyapp.presentation.view.fragment.statisticMember.model

import androidx.annotation.DrawableRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.data.entity.Order
import com.merseyside.partyapp.presentation.view.fragment.statisticMember.adapter.OrderAdapter

@BindingAdapter("app:drawableBackground")
fun setCardViewBackgroundDrawable(cardView: CardView, @DrawableRes res: Int) {
    cardView.background = ContextCompat.getDrawable(CalcApplication.getInstance(), res)
}

@BindingAdapter("app:orders")
fun setOrderList(recyclerView: RecyclerView, orders: List<Order>?) {
    if (!orders.isNullOrEmpty()) {
        if (recyclerView.adapter is OrderAdapter) {
            val adapter = recyclerView.adapter as OrderAdapter

            adapter.removeAll()
            adapter.add(orders)
        }
    }
}