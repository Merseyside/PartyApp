package com.merseyside.partyapp.di

import com.merseyside.partyapp.data.repository.StatisticRepositoryImpl
import com.merseyside.partyapp.domain.repository.StatisticRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.*

@ExperimentalCoroutinesApi
internal val statisticModule = DI.Module("statistic") {

    bind<StatisticRepository>() with singleton {
        StatisticRepositoryImpl(instance(), instance(), instance())
    }
}

@ExperimentalCoroutinesApi
val statisticComponent = DI {

    import(appModule)

    extend(itemComponent, allowOverride = true)
    extend(eventComponent, allowOverride = true)

    import(statisticModule)
}