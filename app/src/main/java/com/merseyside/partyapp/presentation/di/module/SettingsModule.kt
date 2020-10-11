package com.merseyside.partyapp.presentation.di.module

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.merseyside.partyapp.presentation.view.fragment.settings.model.SettingsViewModel
import com.merseyside.partyapp.utils.PrefsHelper
import com.merseyside.archy.presentation.fragment.BaseFragment
import com.merseyside.archy.presentation.model.BundleAwareViewModelFactory
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class SettingsModule(
    private val fragment: BaseFragment,
    private val bundle: Bundle?
) {

    @Provides
    internal fun settingsViewModelProvider(
        router: Router,
        prefsHelper: PrefsHelper
    ): ViewModelProvider.Factory {
        return SettingsViewModelProviderFactory(bundle, router, prefsHelper)
    }

    @Provides
    internal fun provideSettingsViewModel(factory: ViewModelProvider.Factory): SettingsViewModel {
        return ViewModelProviders.of(fragment, factory).get(SettingsViewModel::class.java)
    }

    class SettingsViewModelProviderFactory(
        bundle: Bundle?,
        private val router: Router,
        private val prefsHelper: PrefsHelper
    ) : BundleAwareViewModelFactory<SettingsViewModel>(bundle) {
        override fun getViewModel(): SettingsViewModel {
            return SettingsViewModel(router, prefsHelper)
        }
    }
}