package com.merseyside.partyapp.presentation.view.fragment.addEvent.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.merseyside.animators.AnimatorList
import com.merseyside.animators.Approach
import com.merseyside.animators.Axis
import com.merseyside.animators.MainPoint
import com.merseyside.animators.animator.AlphaAnimator
import com.merseyside.animators.animator.TransitionAnimator
import com.merseyside.archy.presentation.view.OnBackPressedListener
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.databinding.FragmentAddEventBinding
import com.merseyside.partyapp.presentation.base.BaseCalcFragment
import com.merseyside.partyapp.presentation.di.component.DaggerAddEventComponent
import com.merseyside.partyapp.presentation.di.module.AddEventModule
import com.merseyside.partyapp.presentation.view.activity.main.model.SharedViewModel
import com.merseyside.partyapp.presentation.view.fragment.addEvent.model.AddEventViewModel
import com.merseyside.utils.Logger
import com.merseyside.utils.PermissionManager
import com.merseyside.utils.time.Millis

class AddEventFragment : BaseCalcFragment<FragmentAddEventBinding, AddEventViewModel>(), OnBackPressedListener {

    var animatorList: AnimatorList? = null

    private val eventObserver = Observer<Event?> {
        if (it != null) {
            sharedViewModel.eventContainer = it
        }
    }

    private val isContactsLoadedObserver = Observer<Any?> {
        binding.chips.editText.isEnabled = true
    }

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun isShowAdBanner(): Boolean {
        return false
    }

    override fun hasTitleBackButton(): Boolean {
        return true
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun performInjection(bundle: Bundle?) {
        DaggerAddEventComponent.builder()
            .appComponent(appComponent)
            .addEventModule(getAddEventModule(bundle))
            .build().inject(this)
    }

    private fun getAddEventModule(bundle: Bundle?): AddEventModule {
        return AddEventModule(this, bundle)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_add_event
    }

    override fun getTitle(context: Context): String? {
        return if (arguments != null && requireArguments().containsKey(KEY_EDIT_ID)) {
            context.getString(R.string.edit_event_title)
        } else {
            context.getString(R.string.new_event_title)
        }
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
        viewModel.eventLiveData.observe(this, eventObserver)
    }

    private fun doLayout() {
        if (arguments != null && requireArguments().containsKey(KEY_EDIT_ID)) {
            val id = requireArguments().getLong(KEY_EDIT_ID)

            viewModel.initWithEventId(id)
        }
        
        binding.closeEvent.setOnClickListener { 
            showAlertDialog(
                title = getString(R.string.close_event_title),
                message = getString(R.string.close_event_message),
                isCancelable = false,
                positiveButtonText = getString(R.string.close_event),
                negativeButtonText = getString(R.string.cancel),
                onPositiveClick = {
                    startAnimation()
                }
            )
        }

        binding.save.setOnClickListener {
            binding.chips.editText.setText(StringBuilder(binding.chips.editText.text.toString()).append("\n").toString(), TextView.BufferType.EDITABLE)
            viewModel.onSaveClick()
        }

        val permission = arrayOf(Manifest.permission.READ_CONTACTS)
        if (PermissionManager.isPermissionsGranted(baseActivity, *permission)) {
            getContacts()
        } else {
            PermissionManager.requestPermissions(
                this,
                *permission,
                requestCode = PERMISSION_CODE)
        }
    }

    private fun startAnimation() {

        if (animatorList == null) {
            animatorList = AnimatorList(Approach.TOGETHER).apply {
                addAnimator(
                    TransitionAnimator(
                        TransitionAnimator.Builder(
                        view = binding.closeEvent,
                        duration = duration
                    ).apply {
                        setInPercents(
                            0f to MainPoint.TOP_LEFT,
                            -1f to MainPoint.TOP_LEFT,
                            axis = Axis.Y
                        )
                    })
                )

                addAnimator(
                    AlphaAnimator(
                        AlphaAnimator.Builder(
                        view = binding.closeEvent,
                        duration = Millis(190)
                    ).apply {
                        values(1f, 0f)
                    })
                )

                addAnimator(
                    AlphaAnimator(AlphaAnimator.Builder(
                        view = binding.chipsContainer,
                        duration = Millis(250)
                    ).apply {
                        values(1f, 0f)
                    })
                )

                addAnimator(
                    TransitionAnimator(TransitionAnimator.Builder(
                        view = binding.buttonContainer,
                        duration = Millis(1000)
                    ).apply {
                        setInPercents(
                            0f to MainPoint.TOP_LEFT,
                            axis = Axis.Y
                        )
                    })
                )
            }
        }

        animatorList!!.start()

        viewModel.closeEvent()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    getContacts()
                }
            }
        }
    }

    private fun getContacts() {
        binding.chips.editText.isEnabled = false
        viewModel.getContacts()

        viewModel.contactsLoadedSingleEvent.observe(viewLifecycleOwner, isContactsLoadedObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.contactsLoadedSingleEvent.removeObserver(isContactsLoadedObserver)
    }
    override fun onBackPressed(): Boolean {
        return binding.chips.onBackPressed()
    }

    companion object {
        private const val PERMISSION_CODE = 23674

        const val KEY_EDIT_ID = "action"

        val duration = Millis(500)

        fun newInstance(id: Long? = null): AddEventFragment {
            return AddEventFragment().apply {
                id?.let {
                    arguments = Bundle().apply {
                        putLong(KEY_EDIT_ID, id)
                    }
                }
            }
        }
    }
}