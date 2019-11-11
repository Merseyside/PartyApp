package com.merseyside.partyapp.presentation.view.fragment.statisticMain.model

import android.os.Bundle
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.data.entity.MemberStatistic
import com.merseyside.partyapp.data.entity.Statistic
import com.merseyside.partyapp.domain.interactor.GetStatisticInteractor
import com.merseyside.partyapp.presentation.base.BaseCalcViewModel
import kotlinx.coroutines.cancel
import ru.terrakok.cicerone.Router

class StatisticMainViewModel(
    private val router: Router,
    private val getStatisticUseCase: GetStatisticInteractor
) : BaseCalcViewModel(router) {

    val membersVisibility = ObservableField<Boolean>()
    val memberContainer = ObservableField<List<Member>>()
    val memberStatisticLiveData = MutableLiveData<List<MemberStatistic>>()

    var totalSpend = ObservableField<String>()

    override fun dispose() {
        getStatisticUseCase.cancel()
    }

    override fun readFrom(bundle: Bundle) {
    }

    override fun writeTo(bundle: Bundle) {
    }

    fun getStatistic(event: Event?) {

        if (event != null) {
            getStatisticUseCase.execute(
                params = GetStatisticInteractor.Params(eventId = event.id),
                onComplete = {
                    membersVisibility.set(it.membersStatistic.isNotEmpty())

                    showStats(it)
                },
                onError = {
                    showErrorMsg(errorMsgCreator.createErrorMsg(it))
                }
            )
        }
    }

    private fun showStats(stats: Statistic) {
        memberContainer.set(stats.membersStatistic.map { memberStatistic -> memberStatistic.member })

        setTotalSpend(stats.totalSpend)

        memberStatisticLiveData.value = stats.membersStatistic
    }

    private fun setTotalSpend(totalSpend: Double) {
        this.totalSpend.set(getString(R.string.total_spend, totalSpend.toString()))
    }

    companion object {
        private const val TAG = "StatisticMainViewModel"
    }
}