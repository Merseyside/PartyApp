package com.merseyside.partyapp.presentation.di.module

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import com.merseyside.partyapp.presentation.view.activity.main.model.MainViewModel
import com.merseyside.partyapp.presentation.view.activity.main.model.SharedViewModel
import com.merseyside.archy.presentation.activity.BaseActivity
import com.merseyside.archy.presentation.model.BundleAwareViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class MainModule(
    private val activity: BaseActivity,
    private val bundle: Bundle? = null
) {
    @Provides
    @Named("sharedFactory")
    internal fun sharedViewModel(application: Application): ViewModelProvider.Factory {
        return SharedViewModelProviderFactory(bundle, application)
    }

    @Provides
    @Named("mainFactory")
    internal fun mainViewModelProvider(application: Application,router: Router): ViewModelProvider.Factory {
        return MainViewModelProviderFactory(application, router)
    }

    @Provides
    internal fun provideMainViewModel(@Named("mainFactory") factory: ViewModelProvider.Factory): MainViewModel {
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }

    @Provides
    internal fun provideSharedViewModel(@Named("sharedFactory") factory: ViewModelProvider.Factory): SharedViewModel {
        return ViewModelProvider(activity, factory)[SharedViewModel::class.java]
    }

    class SharedViewModelProviderFactory(
        bundle: Bundle?,
        private val application: Application
    ) : BundleAwareViewModelFactory<SharedViewModel>(bundle) {

        override fun getViewModel(): SharedViewModel {
            return SharedViewModel(application)
        }
    }

    class MainViewModelProviderFactory(
        private val application: Application,
        private val router: Router
    ): BundleAwareViewModelFactory<MainViewModel>() {

        override fun getViewModel(): MainViewModel {
            return MainViewModel(application, router)
        }
    }
}