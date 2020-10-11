package com.merseyside.partyapp.data.entity

import com.merseyside.partyapp.data.db.event.Member
import kotlinx.serialization.*

@Serializable
sealed class Order {

    abstract val ownerId: String
    abstract val member: Member
    abstract val title: String
    abstract val price: Double

    @Serializable
    class OrderOwner(
        override val ownerId: String,
        override val member: Member,
        override val title: String,
        override val price: Double
    ) : Order()

    @Serializable
    class OrderReceiver(
        override val ownerId: String,
        override val member: Member,
        override val title: String,
        override val price: Double
    ) : Order()

    override fun toString(): String {
        return "Order(member=$member, title=$title, price=$price)"
    }
}