package com.merseyside.partyapp.presentation.view.fragment.addEvent.model

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.pchmn.materialchips.ChipsInput
import com.pchmn.materialchips.model.ChipInterface

@BindingAdapter(value = ["membersAttrChanged"]) // AttrChanged required postfix
fun setUnlockedListener(view: ChipsInput, listener: InverseBindingListener?) {
    if (listener != null) {
       view.addChipsListener(object: ChipsInput.ChipsListener {
           override fun onChipAdded(chip: ChipInterface?, newSize: Int) {
               listener.onChange()
           }

           override fun onChipRemoved(chip: ChipInterface?, newSize: Int) {
               listener.onChange()
           }

           override fun onTextChanged(text: CharSequence?) {
               if (text!!.contains(" ")) {
                   view.addChip(text.toString().replace(" ", ""), "")
               }
           }
       })
    }
}

@BindingAdapter("app:members")
fun setMembers(chipView: ChipsInput, members: List<String>?) {
    members?.forEach {
        chipView.addChip(it, "kek")
    }
}


@InverseBindingAdapter(attribute = "app:members")
fun getMembers(view: ChipsInput): List<String> {
    return view.selectedChipList.map { it.label }
}