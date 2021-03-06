package com.merseyside.partyapp.presentation.view.fragment.statisticMain.model

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.data.entity.MemberStatistic
import com.merseyside.partyapp.data.entity.Statistic
import com.merseyside.partyapp.domain.interactor.GetStatisticInteractor
import com.merseyside.partyapp.presentation.base.BaseCalcViewModel
import com.merseyside.partyapp.utils.doubleToStringPrice
import com.merseyside.utils.randomBool

class StatisticMainViewModel(
    application: Application,
    router: Router,
    private val getStatisticUseCase: GetStatisticInteractor
) : BaseCalcViewModel(application, router) {

    var statistic: Statistic? = null

    val membersVisibility = ObservableField(true)
    val memberContainer = ObservableField<List<Member>>()
    val memberStatisticLiveData = MutableLiveData<List<MemberStatistic>>()

    val statisticTitle = ObservableField<String>()
    val memberVisibilityHint = ObservableField<String>()

    var totalSpend = ObservableField<String>()

    var event: Event? = null

    override fun goBack() {
        if (randomBool(0.2f)) showInterstitial()
        super.goBack()
    }

    override fun updateLanguage(context: Context) {
        super.updateLanguage(context)

        statisticTitle.set(context.getString(R.string.statistic))
        memberVisibilityHint.set(context.getString(R.string.no_activity))
    }

    override fun dispose() {}

    override fun readFrom(bundle: Bundle) {}

    override fun writeTo(bundle: Bundle) {}

    fun initWithEvent(event: Event?) {
        if (this.event == null) {
            this.event = event

            getStatistic(event)
        }
    }

    private fun getStatistic(event: Event?) {

        if (event != null) {
            getStatisticUseCase.execute(
                coroutineScope = viewModelScope,
                params = GetStatisticInteractor.Params(eventId = event.id),
                onComplete = {
                    statistic = it
                    logStatisticEvent(it)

                    membersVisibility.set(it.membersStatistic.isNotEmpty())

                    showStats(it)
                },
                onError = {
                    showErrorMsg(errorMsgCreator.createErrorMsg(it))
                },
                onPreExecute = { showProgress() },
                onPostExecute = { hideProgress() }
            )
        }
    }

    private fun logStatisticEvent(stats: Statistic) {
        logEvent("statistic", Bundle().apply {
            putString("total_spend", doubleToStringPrice(stats.totalSpend))
            putString("total_debt", doubleToStringPrice(stats.totalDebt))
            putString("currency", stats.currency)
        })
    }

    private fun showStats(stats: Statistic) {
        memberContainer.set(stats.membersStatistic.map { memberStatistic -> memberStatistic.member })

        setTotalSpend(stats.totalSpend)

        memberStatisticLiveData.value = stats.membersStatistic
    }

    private fun setTotalSpend(totalSpend: Double) {
        this.totalSpend.set(getString(R.string.spend, totalSpend.toString()))
    }

    companion object {
        private const val TAG = "StatisticMainViewModel"
    }
}