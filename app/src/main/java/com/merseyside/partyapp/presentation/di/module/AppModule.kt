package com.merseyside.partyapp.presentation.di.module

import android.app.Application
import android.content.Context
import com.merseyside.partyapp.utils.PrefsHelper
import com.upstream.basemvvmimpl.presentation.di.qualifiers.ApplicationContext
import com.upstream.basemvvmimpl.utils.PreferenceManager
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {

    @Provides
    @ApplicationContext
    fun provideContext() : Context {
        return application
    }

    @Provides
    fun provideApplication() : Application {
        return application
    }

    @Provides
    internal fun provideDbName(): String {
        return "partyDb.db"
    }

    @Provides
    internal fun providePreferenceManager(@ApplicationContext context: Context): PreferenceManager {
        return PreferenceManager.Builder()
            .setContext(context)
            .setFilename("calcPrefs")
            .setShared(true)
            .build()
    }

    @Provides
    internal fun providePrefsHelper(preferenceManager: PreferenceManager): PrefsHelper {
        return PrefsHelper(preferenceManager)
    }

}