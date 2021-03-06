package com.merseyside.partyapp.presentation.view.fragment.statisticMain.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.merseyside.partyapp.data.entity.MemberStatistic
import com.merseyside.partyapp.presentation.view.fragment.statisticMember.view.StatisticMemberFragment
import com.merseyside.adapters.base.BaseFragmentPagerAdapter

class MemberStatisticPagerAdapter(
    fm: FragmentManager,
    behavior: Int
) : BaseFragmentPagerAdapter(fm, behavior) {

    private var data: List<MemberStatistic>? = null

    fun setData(data: List<MemberStatistic>) {
        this.data = data
    }

    override fun getItem(position: Int): Fragment {
        return StatisticMemberFragment.newInstance(data!![position])
    }

    override fun getCount(): Int {
        return data?.size ?: throw IllegalStateException("No data in pager adapter")
    }
}