package com.merseyside.partyapp.presentation.view.fragment.eventList.model

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.databinding.Bindable
import com.merseyside.adapters.model.BaseComparableAdapterViewModel
import com.merseyside.archy.presentation.interfaces.IStringHelper
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.entity.Status
import com.merseyside.partyapp.utils.getDateTime

class EventItemViewModel(override var obj: Event)
    : BaseComparableAdapterViewModel<Event>(obj), IStringHelper {

    override fun notifyUpdate() {

        notifyPropertyChanged(BR.name)
        notifyPropertyChanged(BR.memberInfo)
        notifyPropertyChanged(BR.status)
        notifyPropertyChanged(BR.statusColor)
        notifyPropertyChanged(BR.statusIcon)
    }

    override fun areContentsTheSame(obj: Event): Boolean {
        return this.obj == obj
    }

    override fun compareTo(obj: Event): Int {
        return 0
    }

    override fun areItemsTheSame(obj: Event): Boolean {
        return this.obj.id == obj.id
    }

    @Bindable
    fun getName(): String {
        return obj.name
    }

    @Bindable
    fun getMemberInfo(): String {
        return "${getString(R.string.member_count)} ${obj.members.size}"
    }

    @Bindable
    fun getDate(): String {
        return "${getString(R.string.date)} ${getDateTime(obj.timestamp)}"
    }

    @Bindable
    @DrawableRes
    fun getStatusIcon(): Int? {

        return when(obj.status) {
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

        return when (obj.status) {
            Status.IN_PROCESS -> {
                getString(R.string.in_progress)
            }

            else -> getString(R.string.completed)
        }
    }

    @Bindable
    fun getStatusColor(): Int {

        return when(obj.status) {
            Status.IN_PROCESS -> {
                R.attr.colorPrimary
            }
            else -> {
                R.attr.colorSecondaryVariant
            }
        }
    }

    companion object {
        private const val TAG = "EventItemViewModel"
    }

    override fun getLocaleContext(): Context {
        return CalcApplication.getInstance()
    }

}