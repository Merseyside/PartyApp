package com.merseyside.partyapp.presentation.view.fragment.eventList.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.partyapp.data.db.event.Event
import com.upstream.basemvvmimpl.presentation.adapter.UpdateRequest

@BindingAdapter("app:events")
fun setEvents(recyclerView: RecyclerView, events: List<Event>?) {

    if (recyclerView.adapter is EventAdapter) {

        val eventsAdapter = recyclerView.adapter as EventAdapter
        if (events != null) {
            if (eventsAdapter.hasItems()) {
                val request = UpdateRequest.Builder<Event>()
                    .isAddNew(true)
                    .isDeleteOld(true)
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