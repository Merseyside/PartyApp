package com.merseyside.partyapp.presentation.view.fragment.addItem.model

import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.presentation.view.fragment.addItem.adapter.MemberAdapter
import com.merseyside.partyapp.presentation.view.fragment.addItem.adapter.MemberSpinnerAdapter
import com.pchmn.materialchips.ChipsInput
import com.pchmn.materialchips.model.Chip
import com.merseyside.mvvmcleanarch.presentation.adapter.BaseSelectableAdapter
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import com.merseyside.partyapp.data.db.item.MemberInfo

@BindingAdapter(value = ["selectableMembersAttrChanged"]) // AttrChanged required postfix
fun setSelectableMembersListener(view: ChipsInput, listener: InverseBindingListener?) {
    if (listener != null) {
        view.setOnChipSelectedListener { _, _ ->
            listener.onChange()
        }
    }
}

@BindingAdapter("app:selectableMembers")
fun setSelectableMembers(chipView: ChipsInput, members: List<Pair<MemberInfo, Boolean>>?) {
    members?.forEach {
        chipView.addChip(Chip(it.first.id as Any, it.first.name, it.first).apply {
            isSelected = it.second
        })
    }
}


@InverseBindingAdapter(attribute = "app:selectableMembers")
fun getSelectableMembers(view: ChipsInput): List<Pair<MemberInfo, Boolean>> {
    return view.selectedChips.map { Pair(it.`object` as MemberInfo, true)}
}

///

@BindingAdapter("app:payMembers")
fun setPayMembers(recyclerView: RecyclerView, members: List<Member>?) {
    if (members != null) {
        if (recyclerView.adapter is MemberAdapter) {

            val membersAdapter = recyclerView.adapter as MemberAdapter
            membersAdapter.add(members)
        }
    }
}

///

@BindingAdapter("app:payMember")
fun setPayMember(recyclerView: RecyclerView, member: Member?) {
    if (member != null) {
        if (recyclerView.adapter is MemberAdapter) {
            val adapter = recyclerView.adapter!! as MemberAdapter

            adapter.selectItem(member)

            try {
                recyclerView.scrollToPosition(adapter.getPositionOfObj(member))
            } catch (e: IllegalArgumentException) {}
        }
    }
}

@BindingAdapter(value = ["payMemberAttrChanged"]) // AttrChanged required postfix
fun setPayMemberListener(recyclerView: RecyclerView, listener: InverseBindingListener?) {

    val adapter = MemberAdapter()
    recyclerView.adapter = adapter

    adapter.setOnItemSelectedListener(object: BaseSelectableAdapter.OnItemSelectedListener<Member> {
        override fun onSelected(isSelected: Boolean, item: Member) {
            if (isSelected) {
                listener?.onChange()
            }
        }
    })
}

@InverseBindingAdapter(attribute = "app:payMember")
fun getPayMember(recyclerView: RecyclerView): Member? {

    if (recyclerView.adapter is MemberAdapter) {

        val adapter = recyclerView.adapter!! as MemberAdapter

        return adapter.getSelectedItem()
    } else {
        throw IllegalStateException()
    }
}

//

@BindingAdapter("app:spinnerSelectableMembers")
fun setSpinnerMembers(spinner: AppCompatSpinner, list: List<MemberInfo>?) {
    val adapter = MemberSpinnerAdapter(spinner.context, R.layout.view_spinner_member, list)
    spinner.adapter = adapter
}

//

@BindingAdapter(value = ["spinnerSelectedMemberAttrChanged"]) // AttrChanged required postfix
fun setSpinnerSelectedMemberListener(spinner: AppCompatSpinner, listener: InverseBindingListener?) {
    spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            listener?.onChange()
        }
    }
}

@InverseBindingAdapter(attribute = "app:spinnerSelectedMember")
fun getSpinnerSelectedMember(spinner: AppCompatSpinner): MemberInfo? {
    return if (spinner.adapter is MemberSpinnerAdapter) {

        val adapter = spinner.adapter

        adapter.getItem(spinner.selectedItemPosition) as MemberInfo
    } else {
        null
    }
}

@BindingAdapter("app:spinnerSelectedMember")
fun setSpinnerSelectedMember(spinner: Spinner, member: MemberInfo?) {}

//

@BindingAdapter("bind:text")
fun setText(editText: EditText, text: String?) {
    if (editText.text.toString() != text) {
        text?.let {
            editText.setText(it)
            editText.setSelection(it.length)
        }
    }
}

@BindingAdapter(value = ["textAttrChanged"]) // AttrChanged required postfix
fun setTextListener(editText: EditText, listener: InverseBindingListener?) {
    editText.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listener?.onChange()
        }
    })
}

@InverseBindingAdapter(attribute = "bind:text")
fun getText(editText: EditText): String? {
    return editText.text.toString()
}