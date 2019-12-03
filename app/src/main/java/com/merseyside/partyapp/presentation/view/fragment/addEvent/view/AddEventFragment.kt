package com.merseyside.partyapp.presentation.view.fragment.addEvent.view

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.TextView
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
import com.merseyside.partyapp.presentation.view.fragment.addEvent.model.ContactChip
import com.merseyside.mvvmcleanarch.utils.ValueAnimatorHelper


class AddEventFragment : BaseCalcFragment<FragmentAddEventBinding, AddEventViewModel>() {

    private val eventObserver = Observer<Event?> {
        if (it != null) {
            sharedViewModel.eventContainer = it
        }
    }

    private lateinit var sharedViewModel: SharedViewModel

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

        binding.save.setOnClickListener {
            binding.chips.editText.setText(StringBuilder(binding.chips.editText.text.toString()).append(",").toString(), TextView.BufferType.EDITABLE)
            viewModel.onSaveClick()
        }

//        val permission = arrayOf(Manifest.permission.READ_CONTACTS)
//        if (PermissionsManager.isPermissionsGranted(baseActivityView, permission)) {
//            getContactList()
//        } else {
//            PermissionsManager.verifyStoragePermissions(this, permission, PERMISSION_CODE)
//        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContactList()
                }
            }
        }
    }

    private fun getContactList() {

        val contactList = ArrayList<ContactChip>()
        val phones = baseActivityView.contentResolver
            .query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

        if (phones != null) {
            while (phones.moveToNext()) {

                var phoneNumber: String? = null
                val id = phones.getString(phones.getColumnIndex(ContactsContract.Contacts._ID))
                val name =
                    phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val avatarUriString =
                    phones.getString(phones.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI))
                var avatarUri: Uri? = null
                if (avatarUriString != null)
                    avatarUri = Uri.parse(avatarUriString)

                if (Integer.parseInt(phones.getString(phones.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    val pCur = baseActivityView.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf<String>(id),
                        null
                    )

                    while (pCur != null && pCur.moveToNext()) {
                        phoneNumber =
                            pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    }

                    pCur!!.close()

                }

                val contactChip = ContactChip(id, avatarUri, name, phoneNumber)
                contactList.add(contactChip)
            }
            phones.close()
        }

        binding.chips.filterableList = contactList
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