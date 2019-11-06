package com.merseyside.partyapp.data.db.item

data class Item(
    val id: Long,
    val eventId: Long,
    val name: String,
    val description: String,
    val price: Double,
    val payMember: MemberItemInfo,
    val membersInfo: List<MemberItemInfo>,
    val timestamp: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Item

        if (id != other.id) return false
        if (eventId != other.eventId) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (price != other.price) return false
        if (payMember != other.payMember) return false
        if (membersInfo != other.membersInfo) return false
        if (timestamp != other.timestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + eventId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + payMember.hashCode()
        result = 31 * result + membersInfo.hashCode()
        result = 31 * result + timestamp.hashCode()
        return result
    }
}