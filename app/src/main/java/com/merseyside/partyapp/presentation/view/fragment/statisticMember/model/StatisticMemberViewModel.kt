package com.merseyside.partyapp.presentation.view.fragment.statisticMember.model

import androidx.databinding.ObservableField
import com.merseyside.partyapp.data.entity.MemberStatistic
import com.merseyside.partyapp.data.entity.Order
import com.merseyside.partyapp.data.entity.Result
import com.merseyside.partyapp.presentation.base.BaseCalcViewModel

class StatisticMemberViewModel : BaseCalcViewModel() {

    private lateinit var statistic: MemberStatistic

    val ordersVisibility = ObservableField(false)
    val ordersContainer = ObservableField<List<Order>>()

    val resultsVisibility = ObservableField(false)
    val resultsContainer = ObservableField<List<Result>>()

    override fun dispose() {}

    fun initWithMemberStatistic(statistic: MemberStatistic) {
        this.statistic = statistic

        if (!statistic.orders.isNullOrEmpty()) {
            ordersVisibility.set(true)

            ordersContainer.set(statistic.orders)
        }

        if (!statistic.priceResult.isNullOrEmpty()) {
            resultsVisibility.set(true)

            resultsContainer.set(statistic.priceResult)
        }
    }
}