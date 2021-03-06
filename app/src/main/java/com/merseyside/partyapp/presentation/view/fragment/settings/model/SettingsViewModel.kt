package com.merseyside.partyapp.presentation.view.fragment.settings.model

import android.app.Application
import android.content.Context
import android.content.pm.PackageInfo
import android.os.Bundle
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.github.terrakok.cicerone.Router
import com.merseyside.partyapp.R
import com.merseyside.partyapp.presentation.base.BaseCalcViewModel
import com.merseyside.partyapp.utils.PrefsHelper

class SettingsViewModel(
    application: Application,
    router: Router,
    private val prefsHelper: PrefsHelper
) : BaseCalcViewModel(application, router) {

    val interfaceTitle = ObservableField(getString(R.string.settings_interface))

    val currency = ObservableField(prefsHelper.getCurrency())
    val currencyTitle = ObservableField(getString(R.string.enter_currency))
    val currencyError = ObservableField<String>()

    val version = ObservableField(getString(R.string.version, getAppVersion()))

    init {
        currency.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                currency.get()?.let {
                    if (it.length <= 12) {
                        currencyError.set("")
                        saveCurrency(it)
                    } else {
                        currencyError.set(getString(R.string.currency_error))
                    }
                }
            }
        })
    }

    override fun readFrom(bundle: Bundle) {}

    override fun writeTo(bundle: Bundle) {}

    override fun dispose() {}

    override fun updateLanguage(context: Context) {
        super.updateLanguage(context)

        interfaceTitle.set(context.getString(R.string.settings_interface))
        currencyTitle.set(context.getString(R.string.enter_currency))

        version.set(getString(R.string.version, getAppVersion()))
    }

    private fun saveCurrency(currency: String) {
        prefsHelper.setCurrency(currency)
    }

    private fun getAppVersion(): String {
        val pInfo: PackageInfo = application.packageManager.getPackageInfo(application.packageName, 0)
        return pInfo.versionName
    }
}