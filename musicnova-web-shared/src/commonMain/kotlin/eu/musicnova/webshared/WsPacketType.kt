package eu.musicnova.webshared

import kotlin.reflect.KClass

@Suppress("EXPERIMENTAL_API_USAGE")
enum class WsPacketType(val serialHandler: WsPacketSerialHandler<*>, val kClass: KClass<*>) {
    PLAYER_UPDATE_TRACK(PacketPlayerUpdateTrack.serialHandler(), PacketPlayerUpdateTrack::class)

    ;

    fun toByte() = ordinal.toByte()

    companion object {
        private val clazzToPacketType = values()
            .map { wsPacketType -> Pair(wsPacketType.kClass, wsPacketType) }
            .toMap()

        operator fun get(byte: Byte) = values()[byte.toUByte().toInt()]
        operator fun get(packet: WsPacket) = clazzToPacketType[packet::class]
            ?: error("packet not registered as type")

    }
}