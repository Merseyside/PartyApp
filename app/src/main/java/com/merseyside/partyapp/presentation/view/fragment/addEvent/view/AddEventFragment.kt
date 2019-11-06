package com.merseyside.partyapp.presentation.view.fragment.addEvent.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.databinding.FragmentAddEventBinding
import com.merseyside.partyapp.presentation.base.BaseCalcFragment
import com.merseyside.partyapp.presentation.di.component.DaggerAddEventComponent
import com.merseyside.partyapp.presentation.di.module.AddEventModule
import com.merseyside.partyapp.presentation.view.activity.main.model.SharedViewModel
import com.merseyside.partyapp.presentation.view.fragment.addEvent.model.AddEventViewModel
import com.upstream.basemvvmimpl.utils.ValueAnimatorHelper


class AddEventFragment : BaseCalcFragment<FragmentAddEventBinding, AddEventViewModel>() {

    private val eventObserver = Observer<Event?> {
        if (it != null) {
            sharedViewModel.eventContainer = it
        }
    }

    private lateinit var sharedViewModel: SharedViewModel

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
        return if (arguments != null && arguments!!.containsKey(KEY_EDIT_ID)) {
            context.getString(R.string.edit_event_title)
        } else {
            context.getString(R.string.new_event_title)
        }
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
        viewModel.eventLiveData.observe(this, eventObserver)
    }

    private fun doLayout() {
        if (arguments != null && arguments!!.containsKey(KEY_EDIT_ID)) {
            val id = arguments!!.getLong(KEY_EDIT_ID)

            viewModel.initWithEventId(id)
        }
        
        binding.closeEvent.setOnClickListener { 
            showAlertDialog(
                title = getString(R.string.close_event_title),
                message = getString(R.string.close_event_message),
                isCancelable = false,
                onPositiveClick = {
                    val animation = ValueAnimatorHelper()

                    animation.addAnimation(ValueAnimatorHelper.Builder(binding.closeEvent)
                        .translateAnimationPercent(
                            percents  = *floatArrayOf(0f, -1f),
                            mainPoint = ValueAnimatorHelper.MainPoint.TOP_LEFT,
                            animAxis  = ValueAnimatorHelper.AnimAxis.Y_AXIS,
                            duration  = 500
                        ).build()
                    )

                    animation.addAnimation(ValueAnimatorHelper.Builder(binding.closeEvent)
                        .alphaAnimation(
                            1f, 0f,
                            duration = 190
                        ).build())

                    animation.addAnimation(ValueAnimatorHelper.Builder(binding.chipsContainer)
                        .alphaAnimation(
                            1f, 0f,
                            duration = 250
                        ).build())

                    animation.playTogether()

                    viewModel.closeEvent()
                }
            )
        }

    }

    companion object {
        private const val TAG = "AddEventFragment"

        const val KEY_EDIT_ID = "action"

        fun newInstance(): AddEventFragment {
            return AddEventFragment()
        }
    }
}