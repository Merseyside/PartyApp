package com.merseyside.partyapp.presentation.view.fragment.itemList.model

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.partyapp.presentation.view.fragment.itemList.adapter.ItemAdapter
import com.upstream.basemvvmimpl.presentation.adapter.UpdateRequest

@BindingAdapter("app:items")
fun setEvents(recyclerView: RecyclerView, events: List<Item>?) {

    if (recyclerView.adapter is ItemAdapter) {

        val eventsAdapter = recyclerView.adapter as ItemAdapter
        if (events != null) {
            if (eventsAdapter.hasItems()) {
                val request = UpdateRequest.Builder<Item>()
                    .isAddNew(true)
                    .isDeleteOld(false)
                    .setList(events)
                    .build()

                eventsAdapter.updateAsync(request)
            } else {
                eventsAdapter.add(events)
            }
        } else {
            eventsAdapter.removeAll()
        }
    }
}