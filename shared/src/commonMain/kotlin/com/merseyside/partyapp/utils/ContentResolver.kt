package com.merseyside.partyapp.utils

import com.merseyside.partyapp.data.entity.Contact

interface ContentResolver {

    fun getContacts(): List<Contact>
}