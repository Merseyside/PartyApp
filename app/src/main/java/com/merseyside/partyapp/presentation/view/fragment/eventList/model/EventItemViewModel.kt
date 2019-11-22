package com.merseyside.partyapp.presentation.view.fragment.eventList.model

import androidx.annotation.DrawableRes
import androidx.databinding.Bindable
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.entity.Status
import com.merseyside.partyapp.utils.getDateTime
import com.upstream.basemvvmimpl.presentation.model.BaseComparableAdapterViewModel

class EventItemViewModel(override var obj: Event) : BaseComparableAdapterViewModel<Event>(obj) {

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
        return "${CalcApplication.getInstance().getString(R.string.member_count)} ${obj.members.size}"
    }

    @Bindable
    fun getDate(): String {
        return "${CalcApplication.getInstance().getString(R.string.date)} ${getDateTime(obj.timestamp)}"
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
                CalcApplication.getInstance().getString(R.string.in_progress)
            }

            else -> CalcApplication.getInstance().getString(R.string.completed)
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

}