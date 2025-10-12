package com.merseyside.partyapp.data.entity

import com.merseyside.merseyLib.kotlin.contract.Identifiable
import com.merseyside.merseyLib.kotlin.utils.Id
import com.merseyside.partyapp.data.db.event.Member
import kotlinx.serialization.*
import java.math.BigDecimal
import java.math.RoundingMode

@Serializable
sealed class Order : Identifiable<String> {
    abstract val orderId: Id
    abstract val ownerId: String
    abstract val member: Member
    abstract val title: String
    abstract val price: Double
    abstract val serviceFee: Float

    val totalPrice: Double by lazy { convertPercentToPrice(1f + serviceFee, price) }

    val hasServiceFee: Boolean
        get() = serviceFee > 0f

    override val id: String
        get() = "${member.id}/$orderId"

    @Serializable
    data class OrderOwner(
        override val orderId: Id,
        override val ownerId: String,
        override val member: Member,
        override val title: String,
        override val price: Double,
        override val serviceFee: Float
    ) : Order()

    @Serializable
    data class OrderReceiver(
        override val orderId: Id,
        override val ownerId: String,
        override val member: Member,
        override val title: String,
        override val price: Double,
        override val serviceFee: Float
    ) : Order()

    private fun convertPercentToPrice(percent: Float, total: Double): Double {
        val bigInteger = BigDecimal(percent * total)
        return bigInteger.setScale(2, RoundingMode.HALF_UP).toDouble()
    }
}