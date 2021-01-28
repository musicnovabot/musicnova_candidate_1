import eu.musicnova.webshared.PacketPlayerUpdateTrack
import eu.musicnova.webshared.WsPacket
import eu.musicnova.webshared.WsPacketSerializer
import kotlinx.browser.window
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array
import org.khronos.webgl.Uint8Array
import org.w3c.dom.ARRAYBUFFER
import org.w3c.dom.BinaryType
import org.w3c.dom.MessageEvent
import org.w3c.dom.WebSocket

class ConnectionManager(
    val socket: WebSocket /* or sockjs */,
    val useDirectBinary: Boolean
) {

    init {

        if (useDirectBinary) {
            socket.binaryType = BinaryType.ARRAYBUFFER
        }
        socket.onmessage = { messageEvent ->
            onMessageEvent(messageEvent)
        }
    }

    private val textDecoder = js("new TextDecoder()")

    @JsName("send")
    fun send(packet: WsPacket) {
        val bytes = WsPacketSerializer.serializeWsPacket(packet).unsafeCast<Uint8Array>()
        if (useDirectBinary) {
            socket.send(bytes.buffer)
        } else {

            socket.send(window.btoa(textDecoder.decode(bytes) as String))
        }
    }

    private fun onMessageEvent(event: MessageEvent) {
        val data = event.data
        val binary = when (data) {
            is ArrayBuffer -> {
                Int8Array(data).unsafeCast<ByteArray>()
            }
            is String -> {
                window.atob(data).encodeToByteArray()
            }
            else -> return
        }
        handleMessageBinary(binary)
    }

    private fun handleMessageBinary(byteArray: ByteArray) {
        handleIncomingPacket(WsPacketSerializer.deserializeWsPacket(byteArray))
    }

    private fun handleIncomingPacket(packet: WsPacket) {
        when (packet) {
            is PacketPlayerUpdateTrack -> {
                onImpl.updateTrackHandler?.invoke(packet)
            }
        }
    }

    private val onImpl = OnImpl()

    @JsName("on")
    val on: On = onImpl

    interface On {
        @JsName("updateTrack")
        fun updateTrack(handler: (PacketPlayerUpdateTrack) -> Unit)
    }

    private class OnImpl : On {
        var updateTrackHandler: ((PacketPlayerUpdateTrack) -> Unit)? = null
        override fun updateTrack(handler: (PacketPlayerUpdateTrack) -> Unit) {
            updateTrackHandler = handler
        }

    }

}