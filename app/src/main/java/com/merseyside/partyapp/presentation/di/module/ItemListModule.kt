package com.merseyside.partyapp.presentation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.merseyside.partyapp.domain.interactor.GetEventByIdInteractor
import com.merseyside.partyapp.domain.interactor.GetItemsByEventIdInteractor
import com.merseyside.partyapp.presentation.view.fragment.itemList.model.ItemListViewModel
import com.upstream.basemvvmimpl.presentation.fragment.BaseFragment
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class ItemListModule(private val fragment: BaseFragment) {

    @Provides
    internal fun itemListViewModelProvider(
        router: Router,
        getItemsByEventIdUseCase: GetItemsByEventIdInteractor
    ): ViewModelProvider.Factory {
        return ItemListViewModelProviderFactory(router, getItemsByEventIdUseCase)
    }

    @Provides
    internal fun provideItemListViewModel(factory: ViewModelProvider.Factory): ItemListViewModel {
        return ViewModelProviders.of(fragment, factory).get(ItemListViewModel::class.java)
    }

    @Provides
    internal fun provideGetItemsByEventIdInteractor(): GetItemsByEventIdInteractor {
        return GetItemsByEventIdInteractor()
    }

    class ItemListViewModelProviderFactory(
        private val router: Router,
        private val getItemsByEventIdUseCase: GetItemsByEventIdInteractor
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == ItemListViewModel::class.java) {
                return ItemListViewModel(router, getItemsByEventIdUseCase) as T
            }
            throw IllegalArgumentException("Unknown class title")
        }
    }
}