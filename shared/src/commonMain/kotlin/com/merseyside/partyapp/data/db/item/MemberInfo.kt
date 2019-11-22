package com.merseyside.partyapp.data.db.item

import com.merseyside.partyapp.data.db.event.Member
import kotlinx.serialization.*
import kotlinx.serialization.internal.HexConverter
import kotlinx.serialization.internal.StringDescriptor

@Serializable
data class MemberInfo( // Needs to be fixed!
    private val infoId: String,
    private val infoName: String,
    var percent: Float
) : Member(id = infoId, name = infoName) {

    override fun toString(): String {
        return "MemberInfo(infoId='$infoId', infoName='$infoName', percent=$percent)"
    }

//    @Serializer(forClass = MemberInfo::class)
//    companion object : KSerializer<MemberInfo> {
//        override val descriptor: SerialDescriptor =
//            StringDescriptor.withName("MemberInfo")
//
//        @UseExperimental(ExperimentalStdlibApi::class)
//        override fun serialize(encoder: Encoder, obj: MemberInfo) {
//            encoder.encodeString(HexConverter.printHexBinary(obj.ownerId.encodeToByteArray()))
//            encoder.encodeString(HexConverter.printHexBinary(obj.name.encodeToByteArray()))
//            encoder.encodeFloat(obj.percent)
//        }
//
//        @UseExperimental(ExperimentalStdlibApi::class)
//        override fun deserialize(decoder: Decoder): MemberInfo {
//            return MemberInfo(
//                HexConverter.printHexBinary(HexConverter.parseHexBinary(decoder.decodeString())),
//                HexConverter.printHexBinary(HexConverter.parseHexBinary(decoder.decodeString())),
//                decoder.decodeFloat())
//        }
//    }
}