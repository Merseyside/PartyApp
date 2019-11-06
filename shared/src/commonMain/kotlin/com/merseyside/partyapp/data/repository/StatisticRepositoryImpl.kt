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
                    .filter { item -> item.payMember.id == member.id || item.membersInfo.firstOrNull { membersInfo -> membersInfo.id == member.id } != null }
                    .flatMap { item -> // проходим по всем закупкам, в которых учавствует member

                        val payedMemberId = item.payMember.id
                        val isItemOwner = payedMemberId == member.id

                        item.membersInfo.map { memberInfo ->
                            if (isItemOwner) {
                                Order.OrderOwner(memberInfo, item.name,item.price / item.membersInfo.size)
                            } else {
                                Order.OrderReceiver(memberInfo, item.name, item.price / item.membersInfo.size)
                            }
                        }
                    }

                val priceResult = event.members.mapNotNull { member1 ->

                    var price = 0.0

                    orders.forEach { order ->
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

                var totalSpend = 0.0

                orders.forEach { order ->
                    if (order is Order.OrderOwner) {
                        totalSpend += order.price
                    }
                }

                var totalDebt = 0.0

                priceResult.forEach { result ->
                    if (result is Result.ResultDebtor) {
                        totalDebt += result.price
                    }
                }

                if (totalSpend != 0.0 || totalDebt != 0.0) {
                    MemberStatistic(
                        member = member,
                        totalSpend = totalSpend,
                        totalDebt = totalDebt,
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
}