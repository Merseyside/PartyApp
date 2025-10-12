package com.merseyside.partyapp.presentation.view.fragment.statisticMain.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.merseyside.partyapp.data.entity.MemberStatistic
import com.merseyside.partyapp.presentation.view.fragment.statisticMember.view.StatisticMemberFragment

//class MemberStatisticPagerAdapter(
//    fm: FragmentManager,
//    behavior: Int
//) : BaseFragmentPagerAdapter(fm, behavior) {
//
//    private var data: List<MemberStatistic>? = null
//
//    fun setData(data: List<MemberStatistic>) {
//        this.data = data
//    }
//
//    override fun getItem(position: Int): Fragment {
//        return StatisticMemberFragment.newInstance(data!![position])
//    }
//
//    override fun getCount(): Int {
//        return data?.size ?: throw IllegalStateException("No data in pager adapter")
//    }
//}

class MemberStatisticFragmentAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val count: Int
): FragmentStateAdapter(fragmentManager, lifecycle) {

    private var data: List<MemberStatistic>? = null

    fun setData(data: List<MemberStatistic>) {
        this.data = data
    }

    override fun getItemCount(): Int = count

    override fun createFragment(position: Int): Fragment {
        return StatisticMemberFragment.newInstance(requireNotNull(data)[position])
    }

//    override fun containsItem(itemId: Long): Boolean {
//        return get.map { it.hashCode().toLong() }.contains(itemId)
//    }

}