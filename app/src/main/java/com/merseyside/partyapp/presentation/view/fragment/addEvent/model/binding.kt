package com.merseyside.partyapp.presentation.view.fragment.addEvent.model

import android.net.Uri
import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.textfield.TextInputLayout
import com.merseyside.kmpMerseyLib.utils.Logger
import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.data.entity.Contact
import com.merseyside.partyapp.presentation.view.fragment.addItem.model.setText
import com.merseyside.partyapp.utils.generateId
import com.merseyside.partyapp.utils.setTextWithCursor
import com.merseyside.utils.ext.isNotNullAndEmpty
import com.merseyside.utils.ext.log
import com.pchmn.materialchips.ChipsInput
import com.pchmn.materialchips.model.ChipInterface
import kotlin.contracts.ExperimentalContracts

@BindingAdapter(value = ["memberNamesAttrChanged"]) // AttrChanged required postfix
fun setUnlockedListener(view: ChipsInput, listener: InverseBindingListener?) {
    if (listener != null) {

       view.addChipsListener(object: ChipsInput.ChipsListener {
           var prevText = ""

           override fun onChipAdded(chip: ChipInterface?, newSize: Int) {
               listener.onChange()
           }

           override fun onChipRemoved(chip: ChipInterface?, newSize: Int) {
               listener.onChange()
           }

           override fun onTextChanged(text: CharSequence?) {
               Log.d(TAG, text.toString())
               if (!text.isNullOrEmpty() && text.toString() != prevText) {
                   if (text.contains(".") || text.contains("\n")) {
                       if (text.length > 1) {
                           val label = getValidName(text.toString())
                           if (view.allChips.find { it.label == label } == null) {
                               view.addChip(generateId(), label)
                               prevText = ""
                               return
                           } else {
                               view.editText.setTextWithCursor(prevText)
                               return
                           }
                       } else {
                           view.editText.setText("")
                           return
                       }
                   } else if (text.length == 24) {
                       view.editText.setTextWithCursor(prevText)
                       return
                   } else {
                       if (!text.first().isLetterOrDigit()) {
                           view.editText.setTextWithCursor(text.drop(1))
                           return
                       } else if (text.contains("  ")) {
                               prevText = text.toString().replace("  ", " ")
                               view.editText.setTextWithCursor(prevText)
                           }
                       }
                   }

                   prevText = text.toString()
               }

           private fun getValidName(name: String): String {
               var label = name.replace(".", "").replace("\n", "").replace("  ", "")

               if (label.endsWith(" ")) label = label.dropLast(1)

               return label
           }
       })


    }
}

@BindingAdapter("app:memberNames")
fun setMembers(chipView: ChipsInput, members: List<Member>?) {
    members?.forEach {
        chipView.addChip(ContactChip(it.id, it.avatarUrl?.let { url -> Uri.parse(url) }, it.name, it.phone))
    }
}


@InverseBindingAdapter(attribute = "app:memberNames")
fun getMembers(chipsInput: ChipsInput): List<Member> {
    return chipsInput.allChips.map { chip ->
        chip.let {chipInterface ->
            Member(chipInterface.id?.let { it as String } ?: generateId(), chipInterface.label, chipInterface.avatarUri?.toString(), chipInterface.info)
        }
    }
}

@BindingAdapter("app:errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
    if (errorMessage != null) {
        view.error = errorMessage
    }
}

@BindingAdapter("app:contacts")
fun setContacts(view: ChipsInput, contacts: List<Contact>?) {
    if (contacts.isNotNullAndEmpty()) {
        val chipContacts =
            contacts.map { contact -> ContactChip(contact.id, contact.avatarUriPath?.let { url -> Uri.parse(url)}, contact.name, contact.number) }.log()

        view.filterableList = chipContacts
    }
}

private const val TAG = "AddEventBinding"
