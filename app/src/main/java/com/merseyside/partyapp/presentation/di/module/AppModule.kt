package com.merseyside.partyapp.presentation.di.module

import android.app.Application
import android.content.Context
import com.merseyside.partyapp.utils.PrefsHelper
import com.merseyside.merseyLib.presentation.di.qualifiers.ApplicationContext
import com.merseyside.merseyLib.utils.PreferenceManager
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
    internal fun provideDbName(): String {
        return "partyDb.db"
    }

    @Provides
    internal fun providePreferenceManager(@ApplicationContext context: Context): PreferenceManager {
        return PreferenceManager.Builder(context)
            .setFilename("calcPrefs")
            .build()
    }

    @Provides
    internal fun providePrefsHelper(preferenceManager: PreferenceManager): PrefsHelper {
        return PrefsHelper(preferenceManager)
    }

}