package com.merseyside.partyapp.presentation.view.fragment.addItem.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.databinding.FragmentAddItemBinding
import com.merseyside.partyapp.presentation.base.BaseCalcFragment
import com.merseyside.partyapp.presentation.di.component.DaggerAddItemComponent
import com.merseyside.partyapp.presentation.di.module.AddItemModule
import com.merseyside.partyapp.presentation.view.activity.main.model.SharedViewModel
import com.merseyside.partyapp.presentation.view.fragment.addItem.model.AddItemViewModel

class AddItemFragment : BaseCalcFragment<FragmentAddItemBinding, AddItemViewModel>() {

    private lateinit var sharedViewModel: SharedViewModel

    override fun hasTitleBackButton(): Boolean {
        return true
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun performInjection(bundle: Bundle?) {
        DaggerAddItemComponent.builder()
            .appComponent(appComponent)
            .addItemModule(getAddItemModule(bundle))
            .build().inject(this)
    }

    private fun getAddItemModule(bundle: Bundle?): AddItemModule {
        return AddItemModule(this, bundle)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_add_item
    }

    override fun getTitle(context: Context): String? {
        return context.getString(R.string.add_items_title)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedViewModel = ViewModelProviders.of(baseActivity).get(SharedViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doLayout()
    }

    private fun doLayout() {

        binding.additionalContainer.setOnClickListener {
            if (binding.expandedGroup.visibility == View.VISIBLE) {
                binding.expandedGroup.visibility = View.GONE
                binding.expandableIcon.setImageDrawable(ContextCompat.getDrawable(baseActivity, R.drawable.ic_arrow_down))
            } else {
                binding.expandedGroup.visibility = View.VISIBLE
                binding.expandableIcon.setImageDrawable(ContextCompat.getDrawable(baseActivity, R.drawable.ic_arrow_up))
                binding.scrollView.post { binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN) }
            }
        }

        if (arguments != null && arguments!!.containsKey(MODE_KEY)) {
            if (arguments!!.getInt(MODE_KEY) == EDIT_VALUE) {
                viewModel.init(sharedViewModel.eventContainer!!, sharedViewModel.itemContainer)
            }
        } else {
            viewModel.init(sharedViewModel.eventContainer!!)
        }

        binding.price.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) binding.price.setText("${binding.price.text}\n")
        }
    }

    companion object {

        private const val TAG = "AddItemFragment"

        const val MODE_KEY = "mode"
        const val EDIT_VALUE = 1

        fun newInstance(): AddItemFragment {
            return AddItemFragment()
        }
    }


}