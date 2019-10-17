package com.merseyside.partyapp.presentation.view.fragment.eventList.model

import androidx.databinding.Bindable
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.utils.getDateTime
import com.upstream.basemvvmimpl.presentation.model.BaseComparableAdapterViewModel

class EventItemViewModel(private var event: Event) : BaseComparableAdapterViewModel<Event>() {

    override fun areContentTheSame(obj: Event): Boolean {
        return obj == event
    }

    override fun compareTo(obj: Event): Int {
        return 0
    }

    override fun getItem(): Event {
        return event
    }

    override fun setItem(item: Event) {
        event = item
    }

    override fun areItemsTheSame(obj: Event): Boolean {
        return event.id == obj.id
    }

    @Bindable
    fun getName(): String {
        return event.name
    }

    @Bindable
    fun getMemberInfo(): String {
        return "Member count: ${event.members.size}"
    }

    @Bindable
    fun getDate(): String {
        return getDateTime(event.timestamp)!!
    }

    fun onClick() {
        getClickListener()?.onItemClicked(event)
    }

}