package com.merseyside.partyapp.presentation.di.module

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.merseyside.partyapp.presentation.view.activity.main.model.MainViewModel
import com.merseyside.partyapp.presentation.view.activity.main.model.SharedViewModel
import com.merseyside.merseyLib.presentation.activity.BaseActivity
import com.merseyside.merseyLib.presentation.model.BundleAwareViewModelFactory
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router
import javax.inject.Named

@Module
class MainModule(
    private val activity: BaseActivity,
    private val bundle: Bundle? = null
) {

    @Provides
    @Named("sharedFactory")
    internal fun sharedViewModel(): ViewModelProvider.Factory {
        return SharedViewModelProviderFactory(bundle)
    }

    @Provides
    @Named("mainFactory")
    internal fun mainViewModelProvider(router: Router): ViewModelProvider.Factory {
        return MainViewModelProviderFactory(router)
    }

    @Provides
    internal fun provideMainViewModel(@Named("mainFactory") factory: ViewModelProvider.Factory): MainViewModel {
        return ViewModelProviders.of(activity, factory).get(MainViewModel::class.java)
    }

    @Provides
    internal fun provideSharedViewModel(@Named("sharedFactory") factory: ViewModelProvider.Factory): SharedViewModel {
        return ViewModelProviders.of(activity, factory).get(SharedViewModel::class.java)
    }

    class SharedViewModelProviderFactory(
        bundle: Bundle?
    ) : BundleAwareViewModelFactory<SharedViewModel>(bundle) {

        override fun getViewModel(): SharedViewModel {
            return SharedViewModel()
        }
    }

    class MainViewModelProviderFactory(
        private val router: Router
    ): BundleAwareViewModelFactory<MainViewModel>() {

        override fun getViewModel(): MainViewModel {
            return MainViewModel(router)
        }
    }
}