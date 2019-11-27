package com.merseyside.partyapp.presentation.view.fragment.settings.view

import android.content.Context
import android.os.Bundle
import android.view.View
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.databinding.FragmentSettingsBinding
import com.merseyside.partyapp.presentation.base.BaseCalcFragment
import com.merseyside.partyapp.presentation.di.component.DaggerSettingsComponent
import com.merseyside.partyapp.presentation.di.module.SettingsModule
import com.merseyside.partyapp.presentation.view.fragment.settings.model.SettingsViewModel

class SettingsFragment : BaseCalcFragment<FragmentSettingsBinding, SettingsViewModel>() {

    override fun hasTitleBackButton(): Boolean {
        return true
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun performInjection(bundle: Bundle?) {
        DaggerSettingsComponent.builder()
            .appComponent(appComponent)
            .settingsModule(getSettingsModule(bundle))
            .build().inject(this)
    }

    private fun getSettingsModule(bundle: Bundle?): SettingsModule {
        return SettingsModule(this, bundle)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_settings
    }

    override fun getTitle(context: Context): String? {
        return context.getString(R.string.settings_title)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doLayout()
    }

    private fun doLayout() {
        binding.language.apply {
            currentEntryValue = getLanguage()
            setOnValueChangeListener {
                setLanguage(it)
            }
        }
    }

    override fun updateLanguage(context: Context) {
        super.updateLanguage(context)

        binding.language.updateLanguage(context)
    }

    companion object {
        private const val TAG = "SettingsFragment"

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}