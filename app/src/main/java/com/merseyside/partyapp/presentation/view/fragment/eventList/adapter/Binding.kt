package com.merseyside.partyapp.presentation.view.fragment.eventList.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.partyapp.data.db.event.Event
import com.upstream.basemvvmimpl.presentation.adapter.UpdateRequest
import com.upstream.basemvvmimpl.utils.getColorFromAttr

@BindingAdapter("app:events")
fun setEvents(recyclerView: RecyclerView, events: List<Event>?) {

    if (recyclerView.adapter is EventAdapter) {

        val eventsAdapter = recyclerView.adapter as EventAdapter
        if (!events!!.isNullOrEmpty()) {
            if (eventsAdapter.hasItems()) {
                val request = UpdateRequest.Builder<Event>()
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

@BindingAdapter("bind:vectorDrawable")
fun loadVectorDrawable(iv: ImageView, @DrawableRes resId: Int) {
    iv.setImageResource(resId)
}

@BindingAdapter("bind:attrTextColor")
fun setCustomTextColor(view: TextView, attrId: Int) {
    view.setTextColor(view.context.getColorFromAttr(attrId))
}
