package com.merseyside.partyapp.presentation.view.fragment.itemList.adapter

import androidx.appcompat.widget.PopupMenu
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.partyapp.presentation.view.fragment.itemList.model.ItemViewModel
import com.merseyside.adapters.base.BaseSortedAdapter
import com.merseyside.adapters.view.TypedBindingHolder

class ItemAdapter : BaseSortedAdapter<Item, ItemViewModel>() {

    interface OnItemOptionsClickListener {

        fun onDeleteClick(item: Item)
    }

    private var optionsListener: OnItemOptionsClickListener? = null

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.view_item
    }

    override fun getBindingVariable(): Int {
        return BR.obj
    }

    override fun createItemViewModel(obj: Item): ItemViewModel {
        return ItemViewModel(obj)
    }

    fun setOnItemOptionsClickListener(listener: OnItemOptionsClickListener) {
        this.optionsListener = listener
    }

    override fun onBindViewHolder(holder: TypedBindingHolder<ItemViewModel>, position: Int) {
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
}