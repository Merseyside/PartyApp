package com.merseyside.partyapp.data.entity

data class Contact(
    val id: String,
    val avatarUriPath: String?,
    val name: String,
    val number: String?
)