import eu.musicnova.webshared.WsPacket
import eu.musicnova.webshared.WsPacketSerializer
import org.w3c.dom.WebSocket
import kotlin.js.JsName

object JsEntryPoint {


    @JsName("deserializeWsPacket")
    fun deserializeWsPacket(byteArray: ByteArray) = WsPacketSerializer.deserializeWsPacket(byteArray)

    @JsName("serializeWsPacket")
    fun serializeWsPacket(wsPacket: WsPacket) = WsPacketSerializer.serializeWsPacket(wsPacket)


    @JsName("packetBuilder")
    val packetBuilder = PacketBuilder

    @JsName("manageConnection")
    fun manageConnection(
        socket: WebSocket /* or sockjs */,
        useDirectBinary: Boolean
    ) = ConnectionManager(socket, useDirectBinary)
}