package com.merseyside.partyapp.presentation.view.fragment.addEvent.model

import android.graphics.drawable.Drawable
import android.net.Uri
import com.pchmn.materialchips.model.ChipInterface

data class ContactChip(
    val id: String,
    private val avatarUri: Uri?,
    val name: String,
    val phoneNumber: String?,
    val obj: Any? = null
): ChipInterface {

    override fun getObject(): Any? {
        return obj
    }

    override fun setSelected(isSelected: Boolean) {}

    override fun isSelected(): Boolean {
        return false
    }

    override fun getId(): Any {
        return id
    }

    override fun getAvatarUri(): Uri? {
        return avatarUri
    }

    override fun getAvatarDrawable(): Drawable? {
        return null
    }

    override fun getLabel(): String {
        return name
    }

    override fun getInfo(): String? {
        return phoneNumber
    }
}