package com.merseyside.partyapp.presentation.di.module

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.merseyside.partyapp.domain.interactor.AddItemInteractor
import com.merseyside.partyapp.presentation.view.fragment.addItem.model.AddItemViewModel
import com.merseyside.archy.presentation.fragment.BaseFragment
import com.merseyside.archy.presentation.model.BundleAwareViewModelFactory
import com.merseyside.partyapp.utils.PrefsHelper
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class AddItemModule(
    private val fragment: BaseFragment,
    private val bundle: Bundle?
) {

    @Provides
    internal fun addItemViewModelProvider(
        router: Router,
        prefsHelper: PrefsHelper,
        addItemUseCase: AddItemInteractor
    ): ViewModelProvider.Factory {
        return AddItemViewModelProviderFactory(bundle, router, prefsHelper, addItemUseCase)
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
        bundle: Bundle?,
        private val router: Router,
        private val prefsHelper: PrefsHelper,
        private val addItemUseCase: AddItemInteractor
    ) : BundleAwareViewModelFactory<AddItemViewModel>(bundle) {
        override fun getViewModel(): AddItemViewModel {
            return AddItemViewModel(router, prefsHelper, addItemUseCase)
        }
    }
}