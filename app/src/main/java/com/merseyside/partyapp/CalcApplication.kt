package com.merseyside.partyapp

import android.os.Bundle
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.google.firebase.analytics.FirebaseAnalytics
import com.merseyside.mvvmcleanarch.BaseApplication
import com.merseyside.partyapp.data.db.CalcDatabase
import com.merseyside.partyapp.di.sqlDriver
import com.merseyside.partyapp.presentation.di.component.AppComponent
import com.merseyside.partyapp.presentation.di.component.DaggerAppComponent
import com.merseyside.partyapp.presentation.di.module.AppModule
import com.merseyside.partyapp.utils.PrefsHelper
import com.squareup.sqldelight.android.AndroidSqliteDriver
import io.fabric.sdk.android.Fabric
import javax.inject.Inject

class CalcApplication : BaseApplication() {

    companion object {

        private lateinit var instance: CalcApplication

        fun getInstance() : CalcApplication {
            return instance
        }
    }

    @Inject
    lateinit var databaseName: String

    @Inject
    lateinit var prefsHelper: PrefsHelper

    private val firebaseAnalytics: FirebaseAnalytics by lazy { FirebaseAnalytics.getInstance(this) }

    lateinit var appComponent : AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        instance = this

        appComponent = buildComponent()
        appComponent.inject(this)

        initDB()
        initCrashlytics()
    }

    private fun buildComponent() : AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun getBaseLanguage(): String {
        return "en"
    }

    private fun initDB() {
        val config = SupportSQLiteOpenHelper.Configuration.builder(this)
            .name(databaseName)
            .callback(object : SupportSQLiteOpenHelper.Callback(1) {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    val driver = AndroidSqliteDriver(db)
                    CalcDatabase.Schema.create(driver)
                }

                override fun onUpgrade(db: SupportSQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
            })
            .build()

        val sqlHelper = FrameworkSQLiteOpenHelperFactory().create(config)

        sqlDriver = AndroidSqliteDriver(sqlHelper)
    }

    fun logFirebaseEvent(event: String, bundle: Bundle) {
        firebaseAnalytics.logEvent(event, bundle)
    }

    private fun initCrashlytics() {
        val core = CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()
        Fabric.with(this, Crashlytics.Builder().core(core).build())
    }
}