package com.merseyside.partyapp.presentation.view.fragment.statisticMember.model

import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.partyapp.data.entity.Order
import com.merseyside.partyapp.data.entity.Result
import com.merseyside.partyapp.presentation.view.fragment.statisticMember.adapter.OrderAdapter
import com.merseyside.partyapp.presentation.view.fragment.statisticMember.adapter.ResultAdapter
import com.upstream.basemvvmimpl.utils.getColorFromAttr

@BindingAdapter("app:attrCardBackgroundColor")
fun setCardViewbackgroundColor(cardView: CardView, @AttrRes res: Int) {
    cardView.setCardBackgroundColor(cardView.context.getColorFromAttr(res))
}

@BindingAdapter("app:attrBackgroundColor")
fun setViewGroupBackgroundColor(viewGroup: ViewGroup, @AttrRes res: Int) {
    viewGroup.setBackgroundColor(viewGroup.context.getColorFromAttr(res))
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

@BindingAdapter("app:results")
fun setResults(recyclerView: RecyclerView, results: List<Result>?) {
    if (!results.isNullOrEmpty()) {

        if (recyclerView.adapter is ResultAdapter) {
            val adapter = recyclerView.adapter as ResultAdapter

            adapter.removeAll()
            adapter.add(results)
        }
    }
}