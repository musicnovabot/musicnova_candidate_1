package eu.musicnova.webshared

import kotlinx.serialization.Serializable

@Serializable
sealed class WsPacket()

@Serializable
data class PacketPlayerUpdateTrack(val title: String, val author: String? = null) : WsPacket() {
    companion object {
        fun serialHandler() = WsPacketSerialHandler(serializer())
    }
}

