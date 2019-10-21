package com.merseyside.partyapp.presentation.view.fragment.itemList.model

import androidx.databinding.Bindable
import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.partyapp.utils.getDateTime
import com.upstream.basemvvmimpl.presentation.model.BaseComparableAdapterViewModel

class ItemViewModel(private var item: Item) : BaseComparableAdapterViewModel<Item>() {

    override fun areContentTheSame(obj: Item): Boolean {
        return item == obj
    }

    override fun compareTo(obj: Item): Int {
        return 0
    }

    override fun getItem(): Item {
        return item
    }

    override fun setItem(item: Item) {
        this.item = item
    }

    override fun areItemsTheSame(obj: Item): Boolean {
        return obj.id == item.id
    }

    @Bindable
    fun getTitle(): String {
        return item.name
    }

    @Bindable
    fun getDate(): String {
        return getDateTime(item.timestamp)!!
    }

    @Bindable
    fun getPrice(): String {
        return "${item.price}"
    }
}