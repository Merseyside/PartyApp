package com.merseyside.partyapp.presentation.view.fragment.addItem.adapter

import com.merseyside.adapters.base.BaseSelectableAdapter
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.presentation.view.fragment.addItem.model.MemberItemViewModel

class MemberAdapter : BaseSelectableAdapter<Member, MemberItemViewModel>() {

    override fun getLayoutIdForPosition(position: Int): Int {
        return if (getItemByPosition(position).avatarUrl.isNullOrEmpty()) {
            R.layout.view_text_member
        } else {
            R.layout.view_image_member
        }
    }

    override fun getBindingVariable(): Int {
        return BR.obj
    }

    override fun createItemViewModel(obj: Member): MemberItemViewModel {
        return MemberItemViewModel(obj)
    }
}