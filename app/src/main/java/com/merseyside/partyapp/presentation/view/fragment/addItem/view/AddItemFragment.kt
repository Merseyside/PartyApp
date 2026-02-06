package com.merseyside.partyapp.presentation.view.fragment.addItem.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.databinding.FragmentAddItemBinding
import com.merseyside.partyapp.presentation.base.BaseCalcFragment
import com.merseyside.partyapp.presentation.di.component.DaggerAddItemComponent
import com.merseyside.partyapp.presentation.di.module.AddItemModule
import com.merseyside.partyapp.presentation.view.activity.main.model.SharedViewModel
import com.merseyside.partyapp.presentation.view.fragment.addItem.model.AddItemViewModel
import androidx.core.view.isVisible

class AddItemFragment : BaseCalcFragment<FragmentAddItemBinding, AddItemViewModel>() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun performInjection(bundle: Bundle?, vararg params: Any) {
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

    override fun getTitle(context: Context): String {
        return context.getString(R.string.add_items_title)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doLayout()
    }

    private fun doLayout() {
        with(requireBinding()) {
            additionalContainer.setOnClickListener {
                if (expandedGroup.isVisible) {
                    expandedGroup.visibility = View.GONE
                    expandableIcon.setImageDrawable(
                        ContextCompat.getDrawable(baseActivity, R.drawable.ic_arrow_down)
                    )
                } else {
                    expandedGroup.visibility = View.VISIBLE
                    expandableIcon.setImageDrawable(
                        ContextCompat.getDrawable(
                            baseActivity,
                            R.drawable.ic_arrow_up
                        )
                    )
                    scrollView.post { scrollView.fullScroll(ScrollView.FOCUS_DOWN) }
                }
            }


            if (requireArguments().getInt(MODE_KEY) == EDIT_VALUE) {
                this@AddItemFragment.viewModel.init(
                    sharedViewModel.eventContainer!!,
                    sharedViewModel.itemContainer
                )
            } else {
                this@AddItemFragment.viewModel.init(sharedViewModel.eventContainer!!)
            }

            price.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) price.setText("${price.text}\n")
            }
        }
    }

    companion object {

        private const val TAG = "AddItemFragment"

        const val MODE_KEY = "mode"
        const val ADD_VALUE = 0
        const val EDIT_VALUE = 1

        fun newInstance(mode: Int): AddItemFragment {
            return AddItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(MODE_KEY, mode)
                }
            }
        }
    }
}