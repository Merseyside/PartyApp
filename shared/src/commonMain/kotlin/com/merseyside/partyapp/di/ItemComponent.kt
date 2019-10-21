package com.merseyside.partyapp.di

import com.merseyside.partyapp.data.db.item.ItemDao
import com.merseyside.partyapp.data.repository.ItemRepositoryImpl
import com.merseyside.partyapp.domain.repository.ItemRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

@ExperimentalCoroutinesApi
internal val itemModule = Kodein.Module("event") {
    bind<ItemDao>() with singleton { ItemDao( instance() ) }

    bind<ItemRepository>() with singleton {
        ItemRepositoryImpl(instance())
    }
}

@ExperimentalCoroutinesApi
internal val itemComponent = Kodein {
    extend(appComponent)

    import(itemModule)
}