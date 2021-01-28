package eu.musicnova.webshared

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.ProtoBuf

object WsPacketSerializer {
    @OptIn(ExperimentalSerializationApi::class)
    private val protobuf = ProtoBuf {
        encodeDefaults = false
    }

    fun deserializeWsPacket(byteArray: ByteArray): WsPacket {
        val packetType = WsPacketType[byteArray[0]]
        val content = byteArray.copyOfRange(1, byteArray.size)
        return packetType.serialHandler.deserialize(protobuf, content)
    }

    fun serializeWsPacket(wsPacket: WsPacket): ByteArray {
        val packetType = WsPacketType[wsPacket]
        val packetTypeByte = packetType.toByte()
        return byteArrayOf(packetTypeByte) + packetType.serialHandler.serialize(protobuf, wsPacket)

    }
}