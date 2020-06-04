package com.merseyside.partyapp.presentation.view.fragment.itemList.model

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.partyapp.presentation.view.fragment.itemList.adapter.ItemAdapter
import com.merseyside.merseyLib.adapters.UpdateRequest

@BindingAdapter("app:items")
fun setItems(recyclerView: RecyclerView, items: List<Item>?) {

    if (recyclerView.adapter is ItemAdapter) {

        val itemsAdapter = recyclerView.adapter as ItemAdapter
        if (!items.isNullOrEmpty()) {
            if (itemsAdapter.isNotEmpty()) {
                val request = UpdateRequest.Builder(items)
                    .isAddNew(true)
                    .isDeleteOld(true)
                    .build()

                itemsAdapter.update(request)
            } else {
                itemsAdapter.add(items)
            }
        } else {
            itemsAdapter.clear()
        }
    }
}