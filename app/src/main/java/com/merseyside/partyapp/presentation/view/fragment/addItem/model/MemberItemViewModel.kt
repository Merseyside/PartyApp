package com.merseyside.partyapp.presentation.view.fragment.addItem.model

import androidx.databinding.Bindable
import com.merseyside.partyapp.data.db.event.Member
import com.upstream.basemvvmimpl.presentation.model.BaseAdapterViewModel

class MemberItemViewModel(override var obj: Member) : BaseAdapterViewModel<Member>(obj) {

    override fun areItemsTheSame(obj: Member): Boolean {
        return this.obj.id == obj.id
    }

    override fun notifyUpdate() {    }

    @Bindable
    fun getName(): String {
        return obj.name
    }
}