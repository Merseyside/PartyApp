package com.merseyside.partyapp.presentation.view.fragment.itemList.model

import androidx.databinding.Bindable
import com.merseyside.adapters.core.model.AdapterViewModel
import com.merseyside.merseyLib.kotlin.logger.log
import com.merseyside.merseyLib.time.Time
import com.merseyside.merseyLib.time.ext.toFormattedDate
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.partyapp.utils.getHoursDateTime

class ItemViewModel(item: Item) : AdapterViewModel<Item>(item) {

    private var isLastItem: Boolean = false

    @Bindable
    fun getTitle(): String {
        return item.name
    }

    @Bindable
    fun isLastItem(): Boolean {
        return isLastItem
    }

    @Bindable
    fun getTime(): String {
        item.timestamp.log()
        return item.timestamp.localTimeUnit.toFormattedDate(Time.configuration.hoursMinutesPattern).date
    }

    fun setLast(isLast: Boolean) {
        isLastItem = isLast

        //notifyUpdate()
    }

    override fun onPositionChanged(fromPosition: Int, toPosition: Int) {
        super.onPositionChanged(fromPosition, toPosition)
    }

    override fun onUpdate(oldItem: Item, newItem: Item) {
        super.onUpdate(oldItem, newItem)
        notifyPropertyChanged(BR.title)
        notifyPropertyChanged(BR.date)
        notifyPropertyChanged(BR.price)
        notifyPropertyChanged(BR.lastItem)
    }

//    override fun onPositionChanged(position: Int) {
//        super.onPositionChanged(position)

        //setLast(isLast())
//    }
}