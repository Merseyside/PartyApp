package com.merseyside.partyapp

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import com.merseyside.partyapp.presentation.di.component.AppComponent
import com.merseyside.partyapp.presentation.di.component.DaggerAppComponent
import com.merseyside.partyapp.presentation.di.module.AppModule
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.upstream.basemvvmimpl.BaseApplication
import com.merseyside.partyapp.data.db.CalcDatabase
import com.merseyside.partyapp.di.sqlDriver
import com.merseyside.partyapp.utils.PrefsHelper
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

    lateinit var appComponent : AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        instance = this

        appComponent = buildComponent()
        appComponent.inject(this)

        initDB()
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

                override fun onUpgrade(db: SupportSQLiteDatabase?, oldVersion: Int, newVersion: Int) {
                }

            })
            .build()

        val sqlHelper = FrameworkSQLiteOpenHelperFactory().create(config)

        sqlDriver = AndroidSqliteDriver(sqlHelper)
    }
}