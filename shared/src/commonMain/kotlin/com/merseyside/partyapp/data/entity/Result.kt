package com.merseyside.partyapp.data.entity

import com.merseyside.partyapp.data.db.event.Member

sealed class Result(
    val member: Member,
    val price: Double
) {

    class ResultDebtor(member: Member, price: Double) : Result(member, price)

    class ResultLender(member: Member, price: Double) : Result(member, price)

    override fun toString(): String {
        return "Result(member=$member, price=$price)"
    }
}