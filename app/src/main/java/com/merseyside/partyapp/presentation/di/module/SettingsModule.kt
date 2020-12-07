package com.merseyside.partyapp.presentation.di.module

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import com.merseyside.partyapp.presentation.view.fragment.settings.model.SettingsViewModel
import com.merseyside.partyapp.utils.PrefsHelper
import com.merseyside.archy.presentation.fragment.BaseFragment
import com.merseyside.archy.presentation.model.BundleAwareViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class SettingsModule(
    private val fragment: BaseFragment,
    private val bundle: Bundle?
) {
    @Provides
    internal fun settingsViewModelProvider(
        application: Application,
        router: Router,
        prefsHelper: PrefsHelper
    ): ViewModelProvider.Factory {
        return SettingsViewModelProviderFactory(bundle, application, router, prefsHelper)
    }

    @Provides
    internal fun provideSettingsViewModel(factory: ViewModelProvider.Factory): SettingsViewModel {
        return ViewModelProvider(fragment, factory)[SettingsViewModel::class.java]
    }

    class SettingsViewModelProviderFactory(
        bundle: Bundle?,
        private val application: Application,
        private val router: Router,
        private val prefsHelper: PrefsHelper
    ) : BundleAwareViewModelFactory<SettingsViewModel>(bundle) {
        override fun getViewModel(): SettingsViewModel {
            return SettingsViewModel(application, router, prefsHelper)
        }
    }
}