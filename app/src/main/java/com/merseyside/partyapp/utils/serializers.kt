package com.merseyside.partyapp.utils

import com.merseyside.partyapp.data.db.item.MemberInfo
import kotlinx.serialization.KSerializer
import kotlinx.serialization.internal.BooleanSerializer
import kotlinx.serialization.internal.PairSerializer
import kotlinx.serialization.list

val kSerializer: KSerializer<List<Pair<MemberInfo, Boolean>>> =
    (PairSerializer(MemberInfo.serializer(), BooleanSerializer).list)
