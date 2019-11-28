package com.merseyside.partyapp.presentation.view.fragment.settings.model

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.merseyside.partyapp.R
import com.merseyside.partyapp.presentation.base.BaseCalcViewModel
import com.merseyside.partyapp.utils.PrefsHelper
import com.merseyside.mvvmcleanarch.utils.PreferenceManager
import ru.terrakok.cicerone.Router

class SettingsViewModel(
    router: Router,
    private val prefsHelper: PrefsHelper
) : BaseCalcViewModel(router) {

    val interfaceTitle = ObservableField<String>()

    val currency = ObservableField<String>(prefsHelper.getCurrency())
    val currencyTitle = ObservableField<String>()

    init {
        currency.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                currency.get()?.let {
                    saveCurrency(it)
                }
            }
        })
    }

    override fun readFrom(bundle: Bundle) {}

    override fun writeTo(bundle: Bundle) {}

    override fun dispose() {}

    override fun updateLanguage(context: Context) {
        super.updateLanguage(context)

        interfaceTitle.set(getString(R.string.settings_interface))
        currencyTitle.set(context.getString(R.string.enter_currency))
    }

    private fun saveCurrency(currency: String) {
        prefsHelper.setCurrency(currency)
    }

    companion object {
        private const val TAG = "SettingsViewModel"
    }

}