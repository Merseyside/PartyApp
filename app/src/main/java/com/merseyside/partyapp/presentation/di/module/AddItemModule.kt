package com.merseyside.partyapp.presentation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.merseyside.partyapp.domain.interactor.AddItemInteractor
import com.merseyside.partyapp.presentation.view.fragment.addItem.model.AddItemViewModel
import com.upstream.basemvvmimpl.presentation.fragment.BaseFragment
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class AddItemModule(private val fragment: BaseFragment) {

    @Provides
    internal fun addItemViewModelProvider(
        router: Router,
        addItemUseCase: AddItemInteractor
    ): ViewModelProvider.Factory {
        return AddItemViewModelProviderFactory(router, addItemUseCase)
    }

    @Provides
    internal fun provideAddItemViewModel(factory: ViewModelProvider.Factory): AddItemViewModel {
        return ViewModelProviders.of(fragment, factory).get(AddItemViewModel::class.java)
    }

    @Provides
    internal fun provideAddItemInteractor(): AddItemInteractor {
        return AddItemInteractor()
    }

    class AddItemViewModelProviderFactory(
        private val router: Router,
        private val addItemUseCase: AddItemInteractor
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == AddItemViewModel::class.java) {
                return AddItemViewModel(router, addItemUseCase) as T
            }
            throw IllegalArgumentException("Unknown class title")
        }
    }
}