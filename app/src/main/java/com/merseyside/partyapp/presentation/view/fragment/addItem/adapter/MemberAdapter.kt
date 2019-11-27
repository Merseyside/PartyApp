package com.merseyside.partyapp.presentation.view.fragment.addItem.adapter

import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.presentation.view.fragment.addItem.model.MemberItemViewModel
import com.merseyside.mvvmcleanarch.presentation.adapter.BaseSelectableAdapter

class MemberAdapter : BaseSelectableAdapter<Member, MemberItemViewModel>() {

    override fun isAllowToCancelSelection(): Boolean {
        return false
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.view_member
    }

    override fun getBindingVariable(): Int {
        return BR.obj
    }

    override fun createItemViewModel(obj: Member): MemberItemViewModel {
        return MemberItemViewModel(obj)
    }
}