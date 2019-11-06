package com.merseyside.partyapp.domain.interactor

import com.merseyside.partyapp.data.entity.Statistic
import com.merseyside.partyapp.di.statisticComponent
import com.merseyside.partyapp.domain.base.CoroutineUseCase
import com.merseyside.partyapp.domain.repository.StatisticRepository
import org.kodein.di.erased.instance

class GetStatisticInteractor : CoroutineUseCase<Statistic, GetStatisticInteractor.Params>() {

    private val repository: StatisticRepository by statisticComponent.instance()

    override suspend fun executeOnBackground(params: Params?): Statistic {
        return repository.getStatistic(params!!.eventId)
    }

    data class Params(val eventId: Long)
}