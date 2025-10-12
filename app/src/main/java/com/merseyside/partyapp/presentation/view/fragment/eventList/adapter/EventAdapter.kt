package com.merseyside.partyapp.presentation.view.fragment.eventList.adapter

import androidx.appcompat.widget.PopupMenu
import com.merseyside.adapters.SimpleAdapter
import com.merseyside.adapters.core.base.callback.click.onClick
import com.merseyside.adapters.core.config.AdapterConfig
import com.merseyside.adapters.core.config.init.initAdapter
import com.merseyside.adapters.core.feature.sorting.Sorting
import com.merseyside.adapters.core.feature.sorting.comparator.Comparator
import com.merseyside.adapters.core.holder.ViewHolder
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.presentation.view.fragment.eventList.model.EventItemViewModel

class EventAdapter(
    adapterConfig: AdapterConfig<Event, EventItemViewModel>
) : SimpleAdapter<Event, EventItemViewModel>(adapterConfig) {

    interface OnEventOptionsClickListener {
        fun onEditClick(event: Event)

        fun onDeleteClick(event: Event)

        fun onStatisticClick(event: Event)
    }

    private var optionsListener: OnEventOptionsClickListener? = null

    override fun getLayoutIdForViewType(viewType: Int): Int {
        return R.layout.view_event
    }

    override fun getBindingVariable(): Int = BR.obj

    override fun createItemViewModel(item: Event): EventItemViewModel {
        return EventItemViewModel(item)
    }

    fun setOnEventOptionsClickListener(listener: OnEventOptionsClickListener) {
        this.optionsListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder<Event, EventItemViewModel>, position: Int) {
        super.onBindViewHolder(holder, position)
        
        val item = getItemByPosition(position)

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

    companion object {

        operator fun invoke(onClick: (Event) -> Unit): EventAdapter {
            return initAdapter(::EventAdapter) {
                Sorting {
                    comparator = object : Comparator<Event, EventItemViewModel>() {
                        override fun compare(
                            model1: EventItemViewModel,
                            model2: EventItemViewModel
                        ): Int {
                            return 0
                        }
                    }
                }
            }.apply {
                onClick(onClick)
            }
        }
    }
}