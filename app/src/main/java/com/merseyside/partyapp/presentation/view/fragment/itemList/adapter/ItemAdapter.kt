package com.merseyside.partyapp.presentation.view.fragment.itemList.adapter

import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.partyapp.presentation.view.fragment.itemList.model.ItemViewModel
import com.upstream.basemvvmimpl.presentation.adapter.BaseSortedAdapter
import com.upstream.basemvvmimpl.presentation.view.BaseViewHolder

class ItemAdapter : BaseSortedAdapter<Item, ItemViewModel>() {

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.view_item
    }

    override fun getBindingVariable(): Int {
        return BR.obj
    }

    override fun createItemViewModel(obj: Item): ItemViewModel {
        return ItemViewModel(obj)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        getModelByPosition(position).apply {
            setLast(position == itemCount - 1)
        }
    }
}