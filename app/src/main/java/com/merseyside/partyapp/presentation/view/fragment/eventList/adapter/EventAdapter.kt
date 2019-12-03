package com.merseyside.partyapp.presentation.view.fragment.eventList.adapter

import androidx.appcompat.widget.PopupMenu
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.presentation.view.fragment.eventList.model.EventItemViewModel
import com.merseyside.mvvmcleanarch.presentation.adapter.BaseSortedAdapter
import com.merseyside.mvvmcleanarch.presentation.view.BaseViewHolder

class EventAdapter : BaseSortedAdapter<Event, EventItemViewModel>() {

    interface OnEventOptionsClickListener {
        fun onEditClick(event: Event)

        fun onDeleteClick(event: Event)

        fun onStatisticClick(event: Event)
    }

    private var optionsListener: OnEventOptionsClickListener? = null

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.view_event
    }

    override fun getBindingVariable(): Int {
        return BR.obj
    }

    override fun createItemViewModel(obj: Event): EventItemViewModel {
        return EventItemViewModel(obj)
    }

    fun setOnEventOptionsClickListener(listener: OnEventOptionsClickListener) {
        this.optionsListener = listener
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        
        val item = getObjByPosition(position)

        holder.itemView.rootView.setOnLongClickListener {
            val popup = PopupMenu(holder.itemView.context, holder.itemView.findViewById(R.id.status_container))
            popup.inflate(R.menu.menu_event)

            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_edit -> {
                        optionsListener?.onEditClick(item)
                    }

                    R.id.action_delete -> {
                        optionsListener?.onDeleteClick(item)
                    }

                    R.id.action_statistic -> {
                        optionsListener?.onStatisticClick(item)
                    }

                    else -> {
                        return@setOnMenuItemClickListener false
                    }
                }

                true

            }
            popup.show()

            true
        }
    }
}