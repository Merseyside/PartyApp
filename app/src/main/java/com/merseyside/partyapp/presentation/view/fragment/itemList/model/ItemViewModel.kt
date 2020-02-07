package com.merseyside.partyapp.presentation.view.fragment.itemList.model

import androidx.databinding.Bindable
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.partyapp.utils.getHoursDateTime
import com.merseyside.mvvmcleanarch.presentation.model.BaseComparableAdapterViewModel

class ItemViewModel(
    override var obj: Item
) : BaseComparableAdapterViewModel<Item>(obj) {

    private var isLastItem: Boolean = false

    override fun areContentsTheSame(obj: Item): Boolean {
        return this.obj == obj
    }

    override fun compareTo(obj: Item): Int {
        return 0
    }

    override fun areItemsTheSame(obj: Item): Boolean {
        return obj.id == this.obj.id
    }

    @Bindable
    fun getTitle(): String {
        return obj.name
    }

    @Bindable
    fun getDate(): String {
        return getHoursDateTime(obj.timestamp)!!
    }

    @Bindable
    fun getPrice(): String {
        return "${obj.price}"
    }

    @Bindable
    fun isLast(): Boolean {
        return isLastItem
    }

    @Bindable
    fun getTime(): String {
        return getHoursDateTime(obj.timestamp)!!
    }

    fun setLast(isLast: Boolean) {
        isLastItem = isLast

        notifyUpdate()
    }

    override fun notifyUpdate() {
        notifyPropertyChanged(BR.title)
        notifyPropertyChanged(BR.date)
        notifyPropertyChanged(BR.price)
        notifyPropertyChanged(BR.last)
    }
}