package com.merseyside.partyapp.presentation.view.fragment.statisticMember.model

import android.content.Context
import android.os.Bundle
import androidx.databinding.ObservableField
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.entity.MemberStatistic
import com.merseyside.partyapp.data.entity.Order
import com.merseyside.partyapp.data.entity.Result
import com.merseyside.partyapp.presentation.base.BaseCalcViewModel
import com.merseyside.partyapp.utils.doubleToStringPrice
import com.upstream.basemvvmimpl.data.deserialize
import com.upstream.basemvvmimpl.data.serialize

class StatisticMemberViewModel : BaseCalcViewModel() {

    lateinit var statistic: MemberStatistic

    val ordersVisibility = ObservableField(false)
    val ordersContainer = ObservableField<List<Order>>()
    val ordersTitle = ObservableField<String>()

    val resultsVisibility = ObservableField(false)
    val resultsContainer = ObservableField<List<Result>>()

    val totalSpend = ObservableField<String>()
    val spendTitle = ObservableField<String>()

    val totalDebt = ObservableField<String>()
    val debtTitle = ObservableField<String>()

    val totalLend = ObservableField<String>()
    val lendTitle = ObservableField<String>()

    val shareMemberTitle = ObservableField<String>()

    override fun updateLanguage(context: Context) {
        super.updateLanguage(context)

        ordersTitle.set(context.getString(R.string.all_orders))
        spendTitle.set(context.getString(R.string.spend))
        debtTitle.set(context.getString(R.string.owed))
        lendTitle.set(context.getString(R.string.lend))
        shareMemberTitle.set(context.getString(R.string.share_member))
    }

    override fun dispose() {}

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

        if (!statistic.priceResult.isNullOrEmpty()) {

            resultsVisibility.set(true)
            resultsContainer.set(statistic.priceResult)

            totalSpend.set("${doubleToStringPrice(statistic.totalSpend)} ${statistic.currency}")
            totalDebt.set("${doubleToStringPrice(statistic.totalDebt)} ${statistic.currency}")
            totalLend.set("${doubleToStringPrice(statistic.totalLend)} ${statistic.currency}")
        } else {
            resultsVisibility.set(false)
        }
    }

    companion object {
        private const val TAG = "StatisticMemberViewModel"

        private const val STATISTIC_KEY = "statistic"
    }
}