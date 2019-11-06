package com.merseyside.partyapp.data.entity

import com.merseyside.partyapp.data.db.event.Member

data class MemberStatistic(
    val member: Member,
    val totalSpend: Double,
    val totalDebt: Double,
    val orders: List<Order>,
    val priceResult: List<Result>
) {
    override fun toString(): String {
        return "MemberStatistic(member=$member, totalSpend=$totalSpend, totalDebt=$totalDebt, orders=$orders, priceResult=$priceResult)"
    }
}