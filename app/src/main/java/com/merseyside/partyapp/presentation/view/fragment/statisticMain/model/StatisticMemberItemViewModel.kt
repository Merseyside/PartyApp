package com.merseyside.partyapp.presentation.view.fragment.statisticMain.model

import androidx.annotation.ColorRes
import androidx.databinding.Bindable
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Member
import com.upstream.basemvvmimpl.presentation.adapter.SelectableItemInterface
import com.upstream.basemvvmimpl.presentation.model.BaseComparableAdapterViewModel
import com.upstream.basemvvmimpl.utils.getColorFromAttr

class StatisticMemberItemViewModel(override var obj: Member) : BaseComparableAdapterViewModel<Member>(obj),
    SelectableItemInterface {

    override var isSelected: Boolean = false
    set(value) {
        field = value

        notifyUpdate()
    }

    override fun areContentTheSame(obj: Member): Boolean {
        return this.obj.id == obj.id
    }

    override fun compareTo(obj: Member): Int {
        return 0
    }

    override fun areItemsTheSame(obj: Member): Boolean {
        return this.obj == obj
    }

    override fun notifyUpdate() {
        notifyPropertyChanged(BR.circleColor)
    }

    @Bindable
    fun getTitle(): String {
        return obj.name
    }

    @Bindable
    fun getCircleText(): String {
        return com.merseyside.partyapp.utils.getCircleText(obj.name)
    }

    @Bindable
    @ColorRes
    fun getCircleColor(): Int {
        return if (isSelected) {
            CalcApplication.getInstance().getColorFromAttr(R.attr.colorPrimary)
        } else {
            CalcApplication.getInstance().getColorFromAttr(R.attr.colorPrimaryVariant)
        }
    }

}