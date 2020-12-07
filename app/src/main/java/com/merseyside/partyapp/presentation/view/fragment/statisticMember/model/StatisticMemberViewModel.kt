package com.merseyside.partyapp.presentation.view.fragment.statisticMember.model

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.databinding.ObservableField
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.entity.MemberStatistic
import com.merseyside.partyapp.data.entity.Order
import com.merseyside.partyapp.data.entity.Result
import com.merseyside.partyapp.presentation.base.BaseCalcViewModel
import com.merseyside.partyapp.utils.doubleToStringPrice
import com.merseyside.utils.serialization.deserialize
import com.merseyside.utils.serialization.serialize

class StatisticMemberViewModel(
    application: Application
) : BaseCalcViewModel(application) {

    lateinit var statistic: MemberStatistic

    val ordersVisibility = ObservableField(false)
    val ordersContainer = ObservableField<List<Order>>()
    val ordersTitle = ObservableField(getString(R.string.all_orders))

    val resultsVisibility = ObservableField(false)
    val resultsContainer = ObservableField<List<Result>>()

    val totalSpend = ObservableField<String>()
    val spendTitle = ObservableField(getString(R.string.spend))

    val totalDebt = ObservableField<String>()
    val debtTitle = ObservableField(getString(R.string.owed))

    val totalLend = ObservableField<String>()
    val lendTitle = ObservableField(getString(R.string.lend))

    val shareMemberTitle = ObservableField(getString(R.string.share_member))

    override fun updateLanguage(context: Context) {
        super.updateLanguage(context)

        ordersTitle.set(context.getString(R.string.all_orders))
        spendTitle.set(context.getString(R.string.spend))
        debtTitle.set(context.getString(R.string.owed))
        lendTitle.set(context.getString(R.string.lend))
        shareMemberTitle.set(context.getString(R.string.share_member))
    }

    override fun dispose() {
        ordersTitle.notifyChange()
    }

    override fun readFrom(bundle: Bundle) {
        if (bundle.containsKey(STATISTIC_KEY)) {
            initWithMemberStatistic(bundle.getString(STATISTIC_KEY)!!.deserialize())
        }
    }

    override fun writeTo(bundle: Bundle) {
        bundle.putString(STATISTIC_KEY, statistic.serialize())
    }

    fun initWithMemberStatistic(statistic: MemberStatistic) {
        this.statistic = statistic

        if (statistic.orders.isNullOrEmpty()) {
            ordersVisibility.set(false)
        } else {
            ordersVisibility.set(true)
            ordersContainer.set(statistic.orders)
        }

        if (statistic.priceResult.isNullOrEmpty()) {
            resultsVisibility.set(false)
        } else {
            resultsVisibility.set(true)
            resultsContainer.set(statistic.priceResult)

            totalSpend.set("${doubleToStringPrice(statistic.totalSpend)} ${statistic.currency}")
            totalDebt.set("${doubleToStringPrice(statistic.totalDebt)} ${statistic.currency}")
            totalLend.set("${doubleToStringPrice(statistic.totalLend)} ${statistic.currency}")
        }
    }

    companion object {
        private const val TAG = "StatisticMemberViewModel"

        private const val STATISTIC_KEY = "statistic"
    }
}