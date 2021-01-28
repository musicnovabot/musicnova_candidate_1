package eu.musicnova.webshared

import kotlinx.serialization.KSerializer
import kotlinx.serialization.protobuf.ProtoBuf

@Suppress("EXPERIMENTAL_API_USAGE", "UNCHECKED_CAST")
open class WsPacketSerialHandler<T : WsPacket>(private val serializer: KSerializer<T>) {

    fun serialize(protobuf: ProtoBuf, packet: WsPacket): ByteArray
    = protobuf.encodeToByteArray(serializer, packet as T)
    fun deserialize(protobuf: ProtoBuf, byteArray: ByteArray)
    = protobuf.decodeFromByteArray(serializer, byteArray)

}