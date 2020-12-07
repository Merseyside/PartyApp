package com.merseyside.partyapp.presentation.di.module

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import com.merseyside.archy.presentation.fragment.BaseFragment
import com.merseyside.archy.presentation.model.BundleAwareViewModelFactory
import com.merseyside.partyapp.domain.interactor.DeleteItemInteractor
import com.merseyside.partyapp.domain.interactor.GetItemsByEventIdInteractor
import com.merseyside.partyapp.presentation.view.fragment.itemList.model.ItemListViewModel
import dagger.Module
import dagger.Provides

@Module
class ItemListModule(
    private val fragment: BaseFragment,
    private val bundle: Bundle?
) {
    @Provides
    internal fun itemListViewModelProvider(
        application: Application,
        router: Router,
        getItemsByEventIdUseCase: GetItemsByEventIdInteractor,
        deleteItemUseCase: DeleteItemInteractor
    ): ViewModelProvider.Factory {
        return ItemListViewModelProviderFactory(bundle, application, router, getItemsByEventIdUseCase, deleteItemUseCase)
    }

    @Provides
    internal fun provideItemListViewModel(factory: ViewModelProvider.Factory): ItemListViewModel {
        return ViewModelProvider(fragment, factory)[ItemListViewModel::class.java]
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
        private val application: Application,
        private val router: Router,
        private val getItemsByEventIdUseCase: GetItemsByEventIdInteractor,
        private val deleteItemUseCase: DeleteItemInteractor
    ) : BundleAwareViewModelFactory<ItemListViewModel>(bundle) {
        override fun getViewModel(): ItemListViewModel {
            return ItemListViewModel(application, router, getItemsByEventIdUseCase, deleteItemUseCase)
        }
    }
}