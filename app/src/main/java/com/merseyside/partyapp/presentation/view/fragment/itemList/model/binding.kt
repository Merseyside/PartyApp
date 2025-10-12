package com.merseyside.partyapp.presentation.view.fragment.itemList.model

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.adapters.core.async.doAsync
import com.merseyside.adapters.core.modelList.update.UpdateBehaviour
import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.partyapp.presentation.view.fragment.itemList.adapter.ItemAdapter

@BindingAdapter("app:items")
fun setItems(recyclerView: RecyclerView, items: List<Item>?) {

    if (recyclerView.adapter is ItemAdapter) {

        val itemsAdapter = recyclerView.adapter as ItemAdapter
        itemsAdapter.doAsync {
            if (!items.isNullOrEmpty()) {
                if (itemsAdapter.isNotEmpty()) {
                    itemsAdapter.update(
                        items, UpdateBehaviour(removeOld = true, addNew = true)
                    )
                } else itemsAdapter.add(items)

            } else itemsAdapter.clear()
        }
    }
}