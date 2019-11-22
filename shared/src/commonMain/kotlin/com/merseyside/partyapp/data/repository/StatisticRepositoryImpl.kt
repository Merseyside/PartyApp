package com.merseyside.partyapp.data.repository

import com.merseyside.partyapp.data.db.event.EventDao
import com.merseyside.partyapp.data.db.item.ItemDao
import com.merseyside.partyapp.data.entity.MemberStatistic
import com.merseyside.partyapp.data.entity.Order
import com.merseyside.partyapp.data.entity.Result
import com.merseyside.partyapp.data.entity.Statistic
import com.merseyside.partyapp.domain.repository.StatisticRepository

class StatisticRepositoryImpl(
    private val eventDao: EventDao,
    private val itemDao: ItemDao
) : StatisticRepository {

    override suspend fun getStatistic(eventId: Long): Statistic {
        val event = eventDao.getEventById(eventId)
        val items = itemDao.getItemsById(eventId)

        val membersStatistic = event.members
            .mapNotNull { member ->
                val orders = items
                    .filter { item -> item.payMember.id == member.id || item.membersInfo.any { membersInfo ->
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
                                    item.price * equalPercent
                                } else {
                                    item.price * memberInfo.percent
                                }

                                if (isItemOwner) {
                                    Order.OrderOwner(
                                        member.id,
                                        memberInfo,
                                        item.name,
                                        price
                                    )
                                } else {
                                    Order.OrderReceiver(
                                        member.id,
                                        item.payMember,
                                        item.name,
                                        price
                                    )
                                }
                            } else {
                                null
                            }
                        }
                    }

                val priceResult = event.members.mapNotNull priceResult@{ member1 ->
                    var price = 0.0

                    orders.forEach { order ->
                        if (order.ownerId == order.member.id) return@forEach
                        if (member1.id == order.member.id) {
                            if (order is Order.OrderOwner) {
                                price += order.price
                            } else {
                                price -= order.price
                            }
                        }
                    }

                    when {
                        price > 0 -> Result.ResultLender(member1, price)
                        price < 0 -> Result.ResultDebtor(member1, price * -1)
                        else -> null
                    }
                }

                var totalSpend  = 0.0
                var totalOwed   = 0.0
                var totalLend = 0.0

                orders.forEach { order ->
                    if (order is Order.OrderOwner) {
                        totalSpend += order.price

                        if (order.ownerId != order.member.id) {
                            totalLend += order.price
                        }
                    } else {
                        totalOwed += order.price
                    }
                }

                if (totalSpend != 0.0 || totalOwed != 0.0) {
                    MemberStatistic(
                        member = member,
                        totalSpend = totalSpend,
                        totalDebt = totalOwed,
                        totalLend = totalLend,
                        orders = orders,
                        priceResult = priceResult
                    )
                } else {
                    null
                }
            }

        var totalSpend = 0.0

        membersStatistic.forEach { memberStatistic ->
            totalSpend += memberStatistic.totalSpend
        }

        return Statistic(
            eventId = eventId,
            totalSpend = totalSpend,
            memberCount = event.members.size,
            membersStatistic = membersStatistic
        )
    }

    companion object  {
        private const val TAG = "StatisticRepository"
    }
}