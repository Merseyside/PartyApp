package com.merseyside.partyapp.presentation.view.fragment.addItem.model

import android.net.Uri
import androidx.annotation.AttrRes
import androidx.databinding.Bindable
import com.merseyside.adapters.core.feature.selecting.SelectState
import com.merseyside.adapters.core.feature.selecting.SelectableModel
import com.merseyside.adapters.core.model.AdapterViewModel
import com.merseyside.merseyLib.kotlin.logger.log
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.data.db.event.Member
import com.google.android.material.R.attr as MaterialAttr

class MemberItemViewModel(item: Member, override val selectState: SelectState = SelectState()) :
    AdapterViewModel<Member>(item), SelectableModel {

    init {
        selectState.selectedObservable.observe {
            it.log()
            notifyChanged()
        }
    }

    override fun onUpdate(oldItem: Member, newItem: Member) {
        super.onUpdate(oldItem, newItem)
        notifyChanged()
    }

    @Bindable
    fun getName(): String {
        return item.name
    }

    @Bindable
    fun getCircleText(): String {
        return com.merseyside.partyapp.utils.getCircleText(item.name)
    }

    @Bindable
    @AttrRes
    fun getCircleTextColor(): Int {
        return if (isSelected()) MaterialAttr.colorOnBackground
        else MaterialAttr.colorOnSurface
    }

    @Bindable
    @AttrRes
    fun getCircleColor(): Int {
        return if (isSelected()) android.R.attr.colorPrimary
        else MaterialAttr.colorSecondaryVariant

    }

    @Bindable
    fun getImageUrl(): Uri? {
        return item.avatarUrl?.let { Uri.parse(it) }
    }

    private fun notifyChanged() {
        notifyPropertyChanged(BR.circleColor)
        notifyPropertyChanged(BR.circleTextColor)
    }
}