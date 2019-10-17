package com.merseyside.partyapp.presentation.view.fragment.eventList.adapter

import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.presentation.view.fragment.eventList.model.EventItemViewModel
import com.upstream.basemvvmimpl.presentation.adapter.BaseSortedAdapter

class EventAdapter : BaseSortedAdapter<Event, EventItemViewModel>() {

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.view_event
    }

    override fun getBindingVariable(): Int {
        return BR.obj
    }

    override fun createItemViewModel(obj: Event): EventItemViewModel {
        return EventItemViewModel(obj)
    }
}