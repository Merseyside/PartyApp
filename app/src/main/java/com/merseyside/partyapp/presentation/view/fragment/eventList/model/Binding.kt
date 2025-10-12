package com.merseyside.partyapp.presentation.view.fragment.eventList.model

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.adapters.core.async.addAsync
import com.merseyside.adapters.core.async.clearAsync
import com.merseyside.adapters.core.async.updateAsync
import com.merseyside.adapters.core.modelList.update.UpdateBehaviour
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.presentation.view.fragment.eventList.adapter.EventAdapter

@BindingAdapter("app:events")
fun setEvents(recyclerView: RecyclerView, events: List<Event>?) {

    if (recyclerView.adapter is EventAdapter) {
        val eventsAdapter = recyclerView.adapter as EventAdapter
        if (!events!!.isNullOrEmpty()) {
            if (eventsAdapter.isNotEmpty()) {
                eventsAdapter.updateAsync(events, UpdateBehaviour(removeOld = true, addNew = true))
            } else eventsAdapter.addAsync(events)
        } else {
            eventsAdapter.clearAsync()
        }
    }
}

@BindingAdapter("bind:vectorDrawable")
fun loadVectorDrawable(iv: ImageView, @DrawableRes resId: Int) {
    iv.setImageResource(resId)
}

