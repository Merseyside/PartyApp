package com.merseyside.partyapp.presentation.view.fragment.eventList.model

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.databinding.Bindable
import com.merseyside.adapters.core.model.AdapterViewModel
import com.merseyside.archy.presentation.interfaces.IStringHelper
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.entity.Status
import com.merseyside.partyapp.utils.getDateTime
import com.google.android.material.R.attr as MaterialAttr

class EventItemViewModel(item: Event)
    : AdapterViewModel<Event>(item), IStringHelper {

    override fun onUpdate(oldItem: Event, newItem: Event) {
        super.onUpdate(oldItem, newItem)
        notifyUpdate()
    }

    private fun notifyUpdate() {
        notifyPropertyChanged(BR.name)
        notifyPropertyChanged(BR.memberInfo)
        notifyPropertyChanged(BR.status)
        notifyPropertyChanged(BR.statusColor)
        notifyPropertyChanged(BR.statusIcon)
    }

    @Bindable
    fun getName(): String {
        return item.name
    }

    @Bindable
    fun getMemberInfo(): String {
        return "${getString(R.string.member_count)} ${item.members.size}"
    }

    @Bindable
    fun getDate(): String {
        return "${getString(R.string.date)} ${getDateTime(item.timestamp)}"
    }

    @Bindable
    @DrawableRes
    fun getStatusIcon(): Int? {

        return when(item.status) {
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

        return when (item.status) {
            Status.IN_PROCESS -> {
                getString(R.string.in_progress)
            }

            else -> getString(R.string.completed)
        }
    }

    @Bindable
    fun getStatusColor(): Int {

        return when(item.status) {
            Status.IN_PROCESS -> android.R.attr.colorPrimary
            else -> MaterialAttr.colorSecondaryVariant

        }
    }

    override fun getLocaleContext(): Context {
        return CalcApplication.getInstance()
    }

}