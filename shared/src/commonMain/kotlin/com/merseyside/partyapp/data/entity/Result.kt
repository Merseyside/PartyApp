package com.merseyside.partyapp.data.entity

import com.merseyside.partyapp.data.db.event.Member
import kotlinx.serialization.Serializable

@Serializable
sealed class Result {

    abstract val member: Member
    abstract val price: Double

    @Serializable
    class ResultDebtor(
        override val member: Member,
        override val price: Double
    ) : Result()

    @Serializable
    class ResultLender(
        override val member: Member,
        override val price: Double
    ) : Result()

    override fun toString(): String {
        return "Result(member=$member, price=$price)"
    }
}