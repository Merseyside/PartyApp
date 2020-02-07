package com.merseyside.partyapp.presentation.view.fragment.addEvent.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.merseyside.mvvmcleanarch.presentation.view.OnBackPressedListener
import com.merseyside.mvvmcleanarch.utils.PermissionsManager
import com.merseyside.mvvmcleanarch.utils.animation.AnimatorList
import com.merseyside.mvvmcleanarch.utils.animation.ValueAnimatorHelper
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.databinding.FragmentAddEventBinding
import com.merseyside.partyapp.presentation.base.BaseCalcFragment
import com.merseyside.partyapp.presentation.di.component.DaggerAddEventComponent
import com.merseyside.partyapp.presentation.di.module.AddEventModule
import com.merseyside.partyapp.presentation.view.activity.main.model.SharedViewModel
import com.merseyside.partyapp.presentation.view.fragment.addEvent.model.AddEventViewModel


class AddEventFragment : BaseCalcFragment<FragmentAddEventBinding, AddEventViewModel>(), OnBackPressedListener {

    private val eventObserver = Observer<Event?> {
        if (it != null) {
            sharedViewModel.eventContainer = it
            
        }
    }

    private val isContactsLoadedObserver = Observer<Any?> {
        binding.chips.editText.isEnabled = true
    }

    private lateinit var sharedViewModel: SharedViewModel

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
        if (PermissionsManager.isPermissionsGranted(baseActivityView, permission)) {
            getContacts()
        } else {
            PermissionsManager.verifyStoragePermissions(this, permission, PERMISSION_CODE)
        }
    }

    private fun startAnimation() {
        val animatorHelper = ValueAnimatorHelper()

        val animatorList = AnimatorList(AnimatorList.Approach.TOGETHER)

        animatorList.addAnimator(ValueAnimatorHelper.Builder(binding.closeEvent)
            .translateAnimationPercent(
                pointPercents  = listOf(
                    0f to ValueAnimatorHelper.MainPoint.TOP_LEFT,
                    -1f to ValueAnimatorHelper.MainPoint.TOP_LEFT),
                animAxis  = ValueAnimatorHelper.AnimAxis.Y_AXIS,
                duration  = 500
            ).build()
        )

        animatorList.addAnimator(ValueAnimatorHelper.Builder(binding.closeEvent)
            .alphaAnimation(
                1f, 0f,
                duration = 190
            ).build())

        animatorList.addAnimator(ValueAnimatorHelper.Builder(binding.chipsContainer)
            .alphaAnimation(
                1f, 0f,
                duration = 250
            ).build())

        animatorList.addAnimator(ValueAnimatorHelper.Builder(binding.buttonContainer)
            .translateAnimationPercent(
                pointPercents = listOf(0f to ValueAnimatorHelper.MainPoint.TOP_LEFT),
                animAxis  = ValueAnimatorHelper.AnimAxis.Y_AXIS,
                duration  = 1000
            ).build())

        animatorHelper.addAnimatorList(animatorList)
        animatorHelper.start()

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

        viewModel.contactsLoadedSingleEvent.observe(this, isContactsLoadedObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.contactsLoadedSingleEvent.removeObserver(isContactsLoadedObserver)
    }
    override fun onBackPressed(): Boolean {
        return binding.chips.onBackPressed()
    }

    companion object {
        private const val TAG = "AddEventFragment"

        private const val PERMISSION_CODE = 23674

        const val KEY_EDIT_ID = "action"

        fun newInstance(): AddEventFragment {
            return AddEventFragment()
        }
    }
}