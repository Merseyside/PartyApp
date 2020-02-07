package com.merseyside.partyapp.presentation.view.fragment.addItem.model
import androidx.annotation.AttrRes
import androidx.databinding.Bindable
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.mvvmcleanarch.presentation.adapter.SelectableItemInterface
import com.merseyside.mvvmcleanarch.presentation.model.BaseComparableAdapterViewModel

class MemberItemViewModel(override var obj: Member) : BaseComparableAdapterViewModel<Member>(obj),
    SelectableItemInterface {

    override var isSelected: Boolean = false
    set(value) {
        field = value

        notifyUpdate()
    }

    override fun areContentsTheSame(obj: Member): Boolean {
        return this.obj == obj
    }

    override fun compareTo(obj: Member): Int {
        return 0
    }

    override fun areItemsTheSame(obj: Member): Boolean {
        return this.obj.id == obj.id
    }

    override fun notifyUpdate() {
        notifyPropertyChanged(BR.circleColor)
        notifyPropertyChanged(BR.circleTextColor)
    }

    @Bindable
    fun getName(): String {
        return obj.name
    }

    @Bindable
    fun getCircleText(): String {
        return com.merseyside.partyapp.utils.getCircleText(obj.name)
    }

    @Bindable
    @AttrRes
    fun getCircleTextColor(): Int {
        return if (isSelected) {
            R.attr.colorOnBackground
        } else {
            R.attr.colorOnSurface
        }
    }

    @Bindable
    @AttrRes
    fun getCircleColor(): Int {
        return if (isSelected) {
            R.attr.colorPrimary
        } else {
            R.attr.colorSecondaryVariant
        }
    }

    @Bindable
    fun getImageUrl(): String? {
        return obj.avatarUrl
    }

    companion object {
        private const val TAG = "StatisticMemberItem"
    }
}