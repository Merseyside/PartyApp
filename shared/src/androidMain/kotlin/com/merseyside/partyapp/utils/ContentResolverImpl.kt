package com.merseyside.partyapp.utils

import android.provider.ContactsContract
import com.merseyside.partyapp.data.entity.Contact

class ContentResolverImpl(private val contentResolver: android.content.ContentResolver): ContentResolver {

    override fun getContacts(): List<Contact> {

        val phones = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        val contactList = ArrayList<Contact>()
        if (phones != null) {

            while (phones.moveToNext()) {

                var phoneNumber: String? = null
                val id = phones.getString(phones.getColumnIndex(ContactsContract.Contacts._ID))
                val name =
                    phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val avatarUriString =
                    phones.getString(phones.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI))

                if (Integer.parseInt(phones.getString(phones.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    val pCur = contentResolver.query(
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

                val contact = Contact(id, avatarUriString, name, phoneNumber)
                contactList.add(contact)
            }
            phones.close()


        }

        return contactList
    }

}