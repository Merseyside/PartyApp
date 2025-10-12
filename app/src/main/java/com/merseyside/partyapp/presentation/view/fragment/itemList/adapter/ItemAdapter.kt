package com.merseyside.partyapp.presentation.view.fragment.itemList.adapter

import androidx.appcompat.widget.PopupMenu
import com.merseyside.adapters.SimpleAdapter
import com.merseyside.adapters.core.base.callback.click.onClick
import com.merseyside.adapters.core.config.AdapterConfig
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.partyapp.presentation.view.fragment.itemList.model.ItemViewModel
import com.merseyside.adapters.core.config.init.initAdapter
import com.merseyside.adapters.core.feature.sorting.Sorting
import com.merseyside.adapters.core.feature.sorting.comparator.Comparator
import com.merseyside.adapters.core.holder.ViewHolder

class ItemAdapter(adapterConfig: AdapterConfig<Item, ItemViewModel>) : SimpleAdapter<Item, ItemViewModel>(adapterConfig) {

    interface OnItemOptionsClickListener {

        fun onDeleteClick(item: Item)
    }

    private var optionsListener: OnItemOptionsClickListener? = null

    override fun getLayoutIdForViewType(viewType: Int): Int {
        return R.layout.view_item
    }

    override fun getBindingVariable() = BR.obj

    override fun createItemViewModel(obj: Item): ItemViewModel {
        return ItemViewModel(obj)
    }

    fun setOnItemOptionsClickListener(listener: OnItemOptionsClickListener) {
        this.optionsListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder<Item, ItemViewModel>, position: Int) {
        super.onBindViewHolder(holder, position)

        getModelByPosition(position).apply {
            setLast(position == itemCount - 1)
        }

        val item = getItemByPosition(position)

        holder.itemView.rootView.setOnLongClickListener {
            val popup = PopupMenu(holder.itemView.context, holder.itemView.findViewById(R.id.title))
            popup.inflate(R.menu.menu_item)

            popup.setOnMenuItemClickListener {
                when (it.itemId) {

                    R.id.action_delete -> {
                        optionsListener?.onDeleteClick(item)
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

        operator fun invoke(onClick: (Item) -> Unit): ItemAdapter {
            return initAdapter(::ItemAdapter) {
                Sorting {
                    comparator = object : Comparator<Item, ItemViewModel>() {
                        override fun compare(model1: ItemViewModel, model2: ItemViewModel): Int {
                            return if (model1.item.timestamp.gmtTimeUnit < model2.item.timestamp.gmtTimeUnit) -1
                            else 1
                        }
                    }
                }
            }.apply {
                onClick(onClick)
            }
        }
    }
}