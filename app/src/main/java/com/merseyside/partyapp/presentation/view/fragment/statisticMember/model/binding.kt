package com.merseyside.partyapp.presentation.view.fragment.statisticMember.model

import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.mvvmcleanarch.utils.ext.getColorFromAttr
import com.merseyside.partyapp.data.entity.Order
import com.merseyside.partyapp.data.entity.Result
import com.merseyside.partyapp.presentation.view.fragment.statisticMember.adapter.OrderAdapter
import com.merseyside.partyapp.presentation.view.fragment.statisticMember.adapter.ResultAdapter

@BindingAdapter("app:attrCardBackgroundColor")
fun setCardViewBackgroundColor(cardView: CardView, @AttrRes res: Int) {
    cardView.setCardBackgroundColor(cardView.context.getColorFromAttr(res))
}

@BindingAdapter("app:attrBackgroundColor")
fun setViewGroupBackgroundColor(viewGroup: ViewGroup, @AttrRes res: Int) {
    viewGroup.setBackgroundColor(viewGroup.context.getColorFromAttr(res))
}

@BindingAdapter("app:orders")
fun setOrderList(recyclerView: RecyclerView, orders: List<Order>?) {
    if (!orders.isNullOrEmpty()) {
        val adapter = recyclerView.adapter ?: OrderAdapter().also { recyclerView.adapter = it }

        (adapter as OrderAdapter).apply {
            clear()
            add(orders)
        }
    }
}

@BindingAdapter("app:results")
fun setResults(recyclerView: RecyclerView, results: List<Result>?) {
    if (!results.isNullOrEmpty()) {
        recyclerView.postDelayed({
            val adapter = recyclerView.adapter ?: ResultAdapter().also { recyclerView.adapter = it }

            (adapter as ResultAdapter).apply {
                clear()
                add(results)
            }
        }, 50)

    }
}

private const val TAG = "MemberStatisticBinding"