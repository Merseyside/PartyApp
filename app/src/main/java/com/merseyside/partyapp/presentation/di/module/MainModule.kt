package com.merseyside.partyapp.presentation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.merseyside.partyapp.presentation.view.activity.main.model.MainViewModel
import com.upstream.basemvvmimpl.presentation.activity.BaseActivity
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class MainModule(private val activity: BaseActivity) {

    @Provides
    internal fun mainViewModelProvider(router: Router): ViewModelProvider.Factory {
        return MainViewModelProviderFactory(router)
    }

    @Provides
    internal fun provideMainViewModel(factory: ViewModelProvider.Factory): MainViewModel {
        return ViewModelProviders.of(activity, factory).get(MainViewModel::class.java)
    }

    class MainViewModelProviderFactory(
        private val router: Router
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == MainViewModel::class.java) {
                return MainViewModel(router) as T
            }
            throw IllegalArgumentException("Unknown class title")
        }
    }
}