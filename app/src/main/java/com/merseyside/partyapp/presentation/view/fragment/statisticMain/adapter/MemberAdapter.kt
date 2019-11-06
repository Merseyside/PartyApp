package com.merseyside.partyapp.presentation.view.fragment.statisticMain.adapter

import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.presentation.view.fragment.statisticMain.model.StatisticMemberItemViewModel
import com.upstream.basemvvmimpl.presentation.adapter.BaseSelectableAdapter
import com.upstream.basemvvmimpl.presentation.adapter.BaseSortedAdapter

class MemberAdapter : BaseSelectableAdapter<Member, StatisticMemberItemViewModel>() {

    override fun isAllowToCancelSelection(): Boolean {
        return false
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.view_stat_member
    }

    override fun getBindingVariable(): Int {
        return BR.obj
    }

    override fun createItemViewModel(obj: Member): StatisticMemberItemViewModel {
        return StatisticMemberItemViewModel(obj)
    }
}