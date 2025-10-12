package com.merseyside.partyapp.data.db.item

import com.merseyside.merseyLib.kotlin.contract.Identifiable
import com.merseyside.merseyLib.kotlin.utils.Id
import com.merseyside.merseyLib.time.zone.ZonedTimeUnit
import com.merseyside.partyapp.data.db.event.Member
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.math.RoundingMode

@Serializable
data class Item(
    override val id: Id,
    val eventId: Long,
    val name: String,
    val description: String,
    val price: Double,
    val serviceFee: Float,
    val payMember: Member,
    val membersInfo: List<MemberInfo>,
    val timestamp: ZonedTimeUnit
): Identifiable<Id> {

    val totalPrice: Double by lazy { convertPercentToPrice(1f + serviceFee, price) }

    private fun convertPercentToPrice(percent: Float, total: Double): Double {
        val bigInteger = BigDecimal(percent * total)
        return bigInteger.setScale(2, RoundingMode.HALF_UP).toDouble()
    }
}