package com.merseyside.partyapp.presentation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.merseyside.partyapp.domain.interactor.DeleteItemInteractor
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
        getItemsByEventIdUseCase: GetItemsByEventIdInteractor,
        deleteItemUseCase: DeleteItemInteractor
    ): ViewModelProvider.Factory {
        return ItemListViewModelProviderFactory(router, getItemsByEventIdUseCase, deleteItemUseCase)
    }

    @Provides
    internal fun provideItemListViewModel(factory: ViewModelProvider.Factory): ItemListViewModel {
        return ViewModelProviders.of(fragment, factory).get(ItemListViewModel::class.java)
    }

    @Provides
    internal fun provideGetItemsByEventIdInteractor(): GetItemsByEventIdInteractor {
        return GetItemsByEventIdInteractor()
    }

    @Provides
    internal fun provideDeleteItemInteractor(): DeleteItemInteractor {
        return DeleteItemInteractor()
    }

    class ItemListViewModelProviderFactory(
        private val router: Router,
        private val getItemsByEventIdUseCase: GetItemsByEventIdInteractor,
        private val deleteItemUseCase: DeleteItemInteractor
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == ItemListViewModel::class.java) {
                return ItemListViewModel(router, getItemsByEventIdUseCase, deleteItemUseCase) as T
            }
            throw IllegalArgumentException("Unknown class title")
        }
    }
}