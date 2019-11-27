package com.merseyside.partyapp.presentation.di.module

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.merseyside.partyapp.domain.interactor.DeleteItemInteractor
import com.merseyside.partyapp.domain.interactor.GetEventByIdInteractor
import com.merseyside.partyapp.domain.interactor.GetItemsByEventIdInteractor
import com.merseyside.partyapp.presentation.view.fragment.itemList.model.ItemListViewModel
import com.merseyside.mvvmcleanarch.presentation.fragment.BaseFragment
import com.merseyside.mvvmcleanarch.presentation.model.BundleAwareViewModelFactory
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class ItemListModule(
    private val fragment: BaseFragment,
    private val bundle: Bundle?
) {

    @Provides
    internal fun itemListViewModelProvider(
        router: Router,
        getItemsByEventIdUseCase: GetItemsByEventIdInteractor,
        deleteItemUseCase: DeleteItemInteractor
    ): ViewModelProvider.Factory {
        return ItemListViewModelProviderFactory(bundle, router, getItemsByEventIdUseCase, deleteItemUseCase)
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
        bundle: Bundle?,
        private val router: Router,
        private val getItemsByEventIdUseCase: GetItemsByEventIdInteractor,
        private val deleteItemUseCase: DeleteItemInteractor
    ) : BundleAwareViewModelFactory<ItemListViewModel>(bundle) {
        override fun getViewModel(): ItemListViewModel {
            return ItemListViewModel(router, getItemsByEventIdUseCase, deleteItemUseCase)
        }
    }
}