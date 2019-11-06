package com.merseyside.partyapp.presentation.view.fragment.addItem.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.PagerSnapHelper
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.databinding.FragmentAddItemBinding
import com.merseyside.partyapp.presentation.base.BaseCalcFragment
import com.merseyside.partyapp.presentation.di.component.DaggerAddItemComponent
import com.merseyside.partyapp.presentation.di.module.AddItemModule
import com.merseyside.partyapp.presentation.view.activity.main.model.SharedViewModel
import com.merseyside.partyapp.presentation.view.fragment.addItem.adapter.PaidAdapter
import com.merseyside.partyapp.presentation.view.fragment.addItem.model.AddItemViewModel
import com.merseyside.partyapp.utils.LinePagerDecorator
import com.merseyside.partyapp.utils.SnapOnScrollListener
import com.merseyside.partyapp.utils.attachSnapHelperWithListener

class AddItemFragment : BaseCalcFragment<FragmentAddItemBinding, AddItemViewModel>() {

    private lateinit var sharedViewModel: SharedViewModel

    private val adapter = PaidAdapter()

    override fun isActionBarVisible(): Boolean {
        return true
    }

    override fun hasTitleBackButton(): Boolean {
        return true
    }

    override fun setBindingVariable(): Int {
        return BR.viewModel
    }

    override fun performInjection(bundle: Bundle?) {
        DaggerAddItemComponent.builder()
            .appComponent(appComponent)
            .addItemModule(getAddItemModule(bundle))
            .build().inject(this)
    }

    private fun getAddItemModule(bundle: Bundle?): AddItemModule {
        return AddItemModule(this)
    }

    override fun setLayoutId(): Int {
        return R.layout.fragment_add_item
    }

    override fun getTitle(context: Context): String? {
        return context.getString(R.string.add_items_title)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedViewModel = ViewModelProviders.of(baseActivityView).get(SharedViewModel::class.java)

        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doLayout()
    }

    private fun init() {

    }

    private fun doLayout() {

        binding.membersList.adapter = adapter

        binding.membersList.addItemDecoration(
            LinePagerDecorator(
                baseActivityView
            )
        )

        if (arguments != null && arguments!!.containsKey(MODE_KEY)) {
            if (arguments!!.getInt(MODE_KEY) == EDIT_VALUE) {
                viewModel.init(sharedViewModel.eventContainer!!, sharedViewModel.itemContainer)
            }
        } else {
            viewModel.init(sharedViewModel.eventContainer!!)
        }
    }

    companion object {

        const val MODE_KEY = "mode"
        const val EDIT_VALUE = 1

        fun newInstance(): AddItemFragment {
            return AddItemFragment()
        }
    }
}