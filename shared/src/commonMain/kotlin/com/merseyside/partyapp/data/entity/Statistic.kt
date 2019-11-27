package com.merseyside.partyapp.data.entity

data class Statistic(
    val eventId: Long,
    val totalSpend: Double,
    val totalDebt: Double,
    val memberCount: Int,
    val currency: String,
    val membersStatistic: List<MemberStatistic>
) {
    override fun toString(): String {
        return "Statistic(eventId=$eventId, totalSpend=$totalSpend, memberCount=$memberCount, membersStatistic=$membersStatistic)"
    }
}