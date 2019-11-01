package com.merseyside.partyapp.presentation.view.fragment.addEvent.model

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.textfield.TextInputLayout
import com.pchmn.materialchips.ChipsInput
import com.pchmn.materialchips.model.ChipInterface

@BindingAdapter(value = ["memberNamesAttrChanged"]) // AttrChanged required postfix
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
                   if (text.length != 1) {
                       view.addChip(text.toString().replace(" ", ""), "")
                   } else {
                       view.editText.setText("")
                   }
               }
           }
       })
    }
}

@BindingAdapter("app:memberNames")
fun setMembers(chipView: ChipsInput, members: List<String>?) {
    members?.forEach {
        chipView.addChip(it, "kek")
    }
}


@InverseBindingAdapter(attribute = "app:memberNames")
fun getMembers(view: ChipsInput): List<String> {
    return view.allChips.map { it.label }
}

@BindingAdapter("app:errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
    if (errorMessage != null)
        view.error = errorMessage
}