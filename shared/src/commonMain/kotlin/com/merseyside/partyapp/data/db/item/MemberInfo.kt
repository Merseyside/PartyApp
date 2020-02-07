package com.merseyside.partyapp.data.db.item

import com.merseyside.partyapp.data.db.event.Member
import kotlinx.serialization.Serializable

@Serializable
data class MemberInfo( // Needs to be fixed!
    private val infoId: String,
    private val infoName: String,
    private val infoAvatarUrl: String?,
    private val infoPhone: String?,
    var percent: Float
) : Member(id = infoId, name = infoName, avatarUrl = infoAvatarUrl, phone = infoPhone) {

    override fun toString(): String {
        return "MemberInfo(infoId='$infoId', infoName='$infoName', infoAvatarUrl='$infoAvatarUrl', infoPhone='$infoPhone', percent=$percent)"
    }
}