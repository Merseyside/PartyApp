package com.merseyside.partyapp.data.entity

import com.merseyside.partyapp.data.db.event.Member


sealed class Order(
    val ownerId: String,
    val member: Member,
    val title: String,
    val price: Double
) {
    class OrderOwner(id: String, member: Member, title: String, price: Double) : Order(id, member, title, price)

    class OrderReceiver(id: String, member: Member, title: String, price: Double) : Order(id, member, title, price)

    override fun toString(): String {
        return "Order(member=$member, title=$title, price=$price)"
    }
}