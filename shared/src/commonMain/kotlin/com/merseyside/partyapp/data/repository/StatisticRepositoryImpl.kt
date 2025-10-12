package com.merseyside.partyapp.data.repository

import com.merseyside.partyapp.data.db.event.EventDao
import com.merseyside.partyapp.data.db.item.ItemDao
import com.merseyside.partyapp.data.entity.MemberStatistic
import com.merseyside.partyapp.data.entity.Order
import com.merseyside.partyapp.data.entity.Result
import com.merseyside.partyapp.data.entity.Statistic
import com.merseyside.partyapp.domain.repository.StatisticRepository
import com.merseyside.partyapp.utils.PreferenceHelper

class StatisticRepositoryImpl(
    private val eventDao: EventDao,
    private val itemDao: ItemDao,
    private val prefsHelper: PreferenceHelper
) : StatisticRepository {

    override suspend fun getStatistic(eventId: Long): Statistic {
        val event = eventDao.getEventById(eventId)
        val items = itemDao.getItemsById(eventId)

        val currency = prefsHelper.getCurrency("")

        val membersStatistic = event.members
            .mapNotNull { member ->
                val orders = items
                    .filter { item ->
                        item.payMember.id == member.id || item.membersInfo.any { membersInfo ->
                            membersInfo.id == member.id
                        }
                    }
                    .flatMap { item -> // проходим по всем закупкам, в которых учавствует member

                        val payedMemberId = item.payMember.id
                        val isItemOwner = payedMemberId == member.id

                        var equalsCount = 0
                        var hundred = 1f

                        item.membersInfo.forEach {
                            if (it.percent != 0f) {
                                hundred -= it.percent
                            } else {
                                equalsCount++
                            }
                        }

                        val equalPercent = hundred / equalsCount

                        item.membersInfo.mapNotNull { memberInfo ->

                            if (isItemOwner || memberInfo.id == member.id) {
                                val price = if (memberInfo.percent == 0f) {
                                    item.totalPrice * equalPercent
                                } else {
                                    item.totalPrice * memberInfo.percent
                                }

                                if (isItemOwner) {
                                    Order.OrderOwner(
                                        item.id,
                                        member.id,
                                        memberInfo,
                                        item.name,
                                        price,
                                        item.serviceFee
                                    )
                                } else {
                                    Order.OrderReceiver(
                                        item.id,
                                        member.id,
                                        item.payMember,
                                        item.name,
                                        price,
                                        item.serviceFee
                                    )
                                }
                            } else {
                                null
                            }
                        }
                    }

                val priceResult = event.members.mapNotNull priceResult@{ member1 ->
                    var totalPrice = 0.0

                    orders.forEach { order ->
                        if (order.ownerId == order.member.id) return@forEach
                        if (member1.id == order.member.id) {
                            if (order is Order.OrderOwner) {
                                totalPrice += order.totalPrice
                            } else {
                                totalPrice -= order.totalPrice
                            }
                        }
                    }

                    when {
                        totalPrice > 0 -> Result.ResultLender(member1, totalPrice)
                        totalPrice < 0 -> Result.ResultDebtor(member1, totalPrice * -1)
                        else -> null
                    }
                }

                var totalSpend = 0.0
                var totalOwed = 0.0
                var totalLend = 0.0

                orders.forEach { order ->
                    if (order is Order.OrderOwner) {
                        totalSpend += order.totalPrice

                        if (order.ownerId != order.member.id) {
                            totalLend += order.totalPrice
                        }
                    } else {
                        totalOwed += order.totalPrice
                    }
                }

                if (totalSpend != 0.0 || totalOwed != 0.0) {
                    MemberStatistic(
                        member = member,
                        totalSpend = totalSpend,
                        totalDebt = totalOwed,
                        totalLend = totalLend,
                        orders = orders,
                        priceResult = priceResult,
                        currency = currency
                    )
                } else {
                    null
                }
            }

        var totalSpend = 0.0
        var totalDebt = 0.0

        membersStatistic.forEach { memberStatistic ->
            totalSpend += memberStatistic.totalSpend
            totalDebt += memberStatistic.totalDebt
        }

        return Statistic(
            eventId = eventId,
            totalSpend = totalSpend,
            totalDebt = totalDebt,
            memberCount = event.members.size,
            currency = currency,
            membersStatistic = membersStatistic
        )
    }

    companion object {
        private const val TAG = "StatisticRepository"
    }
}