package com.merseyside.partyapp.data.entity

import com.merseyside.partyapp.data.db.event.Member


sealed class Order(
    val member: Member,
    val title: String,
    val price: Double
) {
    class OrderOwner(member: Member, title: String, price: Double) : Order(member, title, price)

    class OrderReceiver(member: Member, title: String, price: Double) : Order(member, title, price)

    override fun toString(): String {
        return "Order(member=$member, title=$title, price=$price)"
    }
}