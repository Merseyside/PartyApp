package com.merseyside.partyapp.presentation.view.fragment.addItem.model

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.presentation.view.fragment.addItem.adapter.PaidAdapter
import com.merseyside.partyapp.utils.SnapOnScrollListener
import com.merseyside.partyapp.utils.attachSnapHelperWithListener
import com.pchmn.materialchips.ChipsInput
import com.pchmn.materialchips.model.Chip

@BindingAdapter("app:members")
fun setMembers(chipView: ChipsInput, members: List<Member>?) {
    members?.forEach {
        chipView.addChip(it.id as Any, it.name)
    }
}

///

@BindingAdapter(value = ["selectableMembersAttrChanged"]) // AttrChanged required postfix
fun setSelectableMembersListener(view: ChipsInput, listener: InverseBindingListener?) {
    if (listener != null) {
        view.setOnChipSelectedListener { isSelected, chip ->
            listener.onChange()
        }
    }
}

@BindingAdapter("app:selectableMembers")
fun setSelectableMembers(chipView: ChipsInput, members: List<Pair<Member, Boolean>>?) {
    members?.forEach {
        chipView.addChip(Chip(it.first.id as Any, it.first.name).apply {
            isSelected = it.second
        })
    }
}


@InverseBindingAdapter(attribute = "app:selectableMembers")
fun getSelectableMembers(view: ChipsInput): List<Pair<Member, Boolean>> {
    return view.selectedChips.map { Pair(Member(it.id.toString(), it.label), true) }
}

///

@BindingAdapter("app:payMembers")
fun setPayMembers(recyclerView: RecyclerView, members: List<Member>?) {
    if (members != null) {
        if (recyclerView.adapter is PaidAdapter) {

            val membersAdapter = recyclerView.adapter as PaidAdapter
            membersAdapter.add(members)
        }
    }
}

///

@BindingAdapter("app:payMember")
fun setPayMember(recyclerView: RecyclerView, member: Member?) {
    if (member != null) {
        if (recyclerView.adapter is PaidAdapter) {
            val adapter = recyclerView.adapter!! as PaidAdapter
            recyclerView.scrollToPosition(adapter.getPositionOfObj(member))
        }
    }
}

@BindingAdapter(value = ["payMemberAttrChanged"]) // AttrChanged required postfix
fun setPayMemberListener(recyclerView: RecyclerView, listener: InverseBindingListener?) {

    recyclerView.attachSnapHelperWithListener(
        PagerSnapHelper(),
        SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL_STATE_IDLE,
        object: SnapOnScrollListener.OnSnapPositionChangeListener {
            override fun onSnapPositionChange(position: Int) {
                listener?.onChange()
            }
        }
    )

}

@InverseBindingAdapter(attribute = "app:payMember")
fun getPayMember(recyclerView: RecyclerView): Member {

    if (recyclerView.adapter is PaidAdapter) {

        val adapter = recyclerView.adapter!! as PaidAdapter

        return adapter.getObjByPosition((recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition())
    } else {
        throw IllegalStateException()
    }
}