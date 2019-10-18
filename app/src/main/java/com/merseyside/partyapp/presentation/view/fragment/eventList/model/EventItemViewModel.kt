package com.merseyside.partyapp.presentation.view.fragment.eventList.model

import androidx.annotation.DrawableRes
import androidx.databinding.Bindable
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.entity.Status
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
        return "${CalcApplication.getInstance().getString(R.string.member_count)} ${event.members.size}"
    }

    @Bindable
    fun getDate(): String {
        return "${CalcApplication.getInstance().getString(R.string.date)} ${getDateTime(event.timestamp)}"
    }

    fun onClick() {
        getClickListener()?.onItemClicked(event)
    }

    @Bindable
    @DrawableRes
    fun getStatusIcon(): Int? {

        return when(event.status) {
            Status.IN_PROCESS -> {
                R.drawable.ic_process
            }
            Status.COMPLETE -> {
                R.drawable.ic_complete
            }
        }
    }

    @Bindable
    fun getStatus(): String {

        return when (event.status) {
            Status.IN_PROCESS -> {
                CalcApplication.getInstance().getString(R.string.in_progress)
            }

            else -> CalcApplication.getInstance().getString(R.string.completed)
        }
    }

    @Bindable
    fun getStatusColor(): Int {

        return when(event.status) {
            Status.IN_PROCESS -> {
                R.attr.colorSecondary
            }
            else -> {
                R.attr.colorPrimary
            }
        }
    }

}