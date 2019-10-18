package com.merseyside.partyapp.presentation.view.fragment.addEvent.view

import android.content.Context
import android.os.Bundle
import android.view.View
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.databinding.FragmentAddEventBinding
import com.merseyside.partyapp.presentation.base.BaseCalcFragment
import com.merseyside.partyapp.presentation.di.component.DaggerAddEventComponent
import com.merseyside.partyapp.presentation.di.module.AddEventModule
import com.merseyside.partyapp.presentation.view.fragment.addEvent.model.AddEventViewModel


class AddEventFragment : BaseCalcFragment<FragmentAddEventBinding, AddEventViewModel>() {

    override fun hasTitleBackButton(): Boolean {
        return true
    }

    override fun setBindingVariable(): Int {
        return BR.viewModel
    }

    override fun performInjection(bundle: Bundle?) {
        DaggerAddEventComponent.builder()
            .appComponent(appComponent)
            .addEventModule(getAddEventModule(bundle))
            .build().inject(this)
    }

    private fun getAddEventModule(bundle: Bundle?): AddEventModule {
        return AddEventModule(this)
    }

    override fun setLayoutId(): Int {
        return R.layout.fragment_add_event
    }

    override fun getTitle(context: Context): String? {
        return context.getString(R.string.new_event_title)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doLayout()
    }

    private fun init() {

    }

    private fun doLayout() {

    }


    companion object {
        private const val TAG = "AddEventFragment"

         fun newInstance(): AddEventFragment {
             return AddEventFragment()
         }
    }
}