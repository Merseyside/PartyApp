package com.merseyside.partyapp.presentation.view.fragment.statisticMain.model

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.presentation.view.fragment.statisticMain.adapter.MemberAdapter

@BindingAdapter("app:statisticMembers")
fun setStatisticMembers(recyclerView: RecyclerView, members: List<Member>?) {
    if (members != null) {
        if (recyclerView.adapter is MemberAdapter) {
            val adapter = recyclerView.adapter as MemberAdapter

            adapter.removeAll()
            adapter.add(members)
        }
    }
}
