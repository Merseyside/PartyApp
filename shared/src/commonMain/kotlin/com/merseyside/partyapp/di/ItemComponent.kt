package com.merseyside.partyapp.di

import com.merseyside.partyapp.data.db.item.ItemDao
import com.merseyside.partyapp.data.repository.ItemRepositoryImpl
import com.merseyside.partyapp.domain.repository.ItemRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.*

@ExperimentalCoroutinesApi
internal val itemModule = DI.Module("event") {

    bind<ItemDao>() with singleton { ItemDao( instance() ) }

    bind<ItemRepository>() with singleton {
        ItemRepositoryImpl(instance())
    }
}

@ExperimentalCoroutinesApi
internal val itemComponent = DI {
    extend(appComponent)
    import(itemModule)
}