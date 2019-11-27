package com.merseyside.partyapp.domain.repository

import com.merseyside.partyapp.data.entity.Statistic

interface StatisticRepository {

    suspend fun getStatistic(eventId: Long): Statistic
}