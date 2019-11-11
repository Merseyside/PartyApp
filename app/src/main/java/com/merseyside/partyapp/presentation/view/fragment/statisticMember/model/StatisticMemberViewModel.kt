package com.merseyside.partyapp.presentation.view.fragment.statisticMember.model

import android.os.Bundle
import androidx.databinding.ObservableField
import com.merseyside.partyapp.data.entity.MemberStatistic
import com.merseyside.partyapp.data.entity.Order
import com.merseyside.partyapp.data.entity.Result
import com.merseyside.partyapp.presentation.base.BaseCalcViewModel
import com.merseyside.partyapp.utils.doubleToStringPrice

class StatisticMemberViewModel : BaseCalcViewModel() {

    private lateinit var statistic: MemberStatistic

    val ordersVisibility = ObservableField(false)
    val ordersContainer = ObservableField<List<Order>>()

    val resultsVisibility = ObservableField(false)
    val resultsContainer = ObservableField<List<Result>>()

    val totalSpend = ObservableField<String>()
    val totalDebt = ObservableField<String>()
    val totalLend = ObservableField<String>()

    override fun dispose() {}

    override fun readFrom(bundle: Bundle) {}

    override fun writeTo(bundle: Bundle) {}

    fun initWithMemberStatistic(statistic: MemberStatistic) {
        this.statistic = statistic

        if (!statistic.orders.isNullOrEmpty()) {
            ordersVisibility.set(true)

            ordersContainer.set(statistic.orders)
        }

        if (!statistic.priceResult.isNullOrEmpty()) {

            resultsVisibility.set(true)
            resultsContainer.set(statistic.priceResult)

            totalSpend.set(doubleToStringPrice(statistic.totalSpend))
            totalDebt.set(doubleToStringPrice(statistic.totalDebt))
            totalLend.set(doubleToStringPrice(statistic.totalLend))
        }
    }

    companion object {
        private const val TAG = "StatisticMemberViewModel"
    }
}