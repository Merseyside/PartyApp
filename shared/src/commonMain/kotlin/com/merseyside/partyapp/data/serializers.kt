package com.merseyside.partyapp.data

import com.merseyside.partyapp.data.entity.Order
import kotlinx.serialization.KSerializer
import kotlinx.serialization.list

val orderSerializer: KSerializer<List<Order>> =
    (Order.serializer().list)