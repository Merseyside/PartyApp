package com.merseyside.partyapp.data.entity

import com.merseyside.partyapp.data.db.event.Member
import kotlinx.serialization.Serializable

@Serializable
data class MemberStatistic(
    val member: Member,
    val totalSpend: Double,
    val totalDebt: Double,
    val totalLend: Double,
    val orders: List<Order>,
    val priceResult: List<Result>,
    val currency: String
) {
    override fun toString(): String {
        return "MemberStatistic(member=$member, totalSpend=$totalSpend, totalDebt=$totalDebt, orders=$orders, priceResult=$priceResult)"
    }


}