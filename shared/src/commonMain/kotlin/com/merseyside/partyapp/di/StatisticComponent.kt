package com.merseyside.partyapp.di

import com.merseyside.partyapp.data.repository.StatisticRepositoryImpl
import com.merseyside.partyapp.domain.repository.StatisticRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

@ExperimentalCoroutinesApi
internal val statisticModule = Kodein.Module("statistic") {

    bind<StatisticRepository>() with singleton {
        StatisticRepositoryImpl(instance(), instance(), instance())
    }
}

@ExperimentalCoroutinesApi
val statisticComponent = Kodein {

    import(appModule)

    extend(itemComponent, allowOverride = true)
    extend(eventComponent, allowOverride = true)

    import(statisticModule)
}