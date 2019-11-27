package com.merseyside.partyapp.presentation.view.fragment.eventList.model

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.presentation.view.fragment.eventList.adapter.EventAdapter
import com.upstream.basemvvmimpl.presentation.adapter.UpdateRequest
import com.upstream.basemvvmimpl.utils.getColorFromAttr

@BindingAdapter("app:events")
fun setEvents(recyclerView: RecyclerView, events: List<Event>?) {

    if (recyclerView.adapter is EventAdapter) {

        val eventsAdapter = recyclerView.adapter as EventAdapter
        if (!events!!.isNullOrEmpty()) {
            if (eventsAdapter.isNotEmpty()) {
                val request = UpdateRequest.Builder<Event>()
                    .isAddNew(true)
                    .isDeleteOld(true)
                    .setList(events)
                    .build()

                eventsAdapter.update(request)
            } else {
                eventsAdapter.add(events)
            }
        } else {
            eventsAdapter.removeAll()
        }
    }
}

@BindingAdapter("bind:vectorDrawable")
fun loadVectorDrawable(iv: ImageView, @DrawableRes resId: Int) {
    iv.setImageResource(resId)
}

