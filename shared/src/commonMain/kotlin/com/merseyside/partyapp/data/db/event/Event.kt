package com.merseyside.partyapp.data.db.event

import com.merseyside.partyapp.data.entity.Status
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: Long,
    var name: String,
    var members: MutableList<Member>,
    var notes: String,
    var status: Status,
    val timestamp: Long
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Event

        if (id != other.id) return false
        if (name != other.name) return false
        if (members != other.members) return false
        if (notes != other.notes) return false
        if (status != other.status) return false
        if (timestamp != other.timestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + members.hashCode()
        result = 31 * result + notes.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + timestamp.hashCode()
        return result
    }
}