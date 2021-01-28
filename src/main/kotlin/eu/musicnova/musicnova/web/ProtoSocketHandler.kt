package eu.musicnova.musicnova.web

import com.google.common.io.BaseEncoding
import eu.musicnova.webshared.WsPacketSerializer
import org.springframework.stereotype.Component
import org.springframework.web.socket.BinaryMessage
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.AbstractWebSocketHandler


abstract class ProtoSocketHandler : AbstractWebSocketHandler() {

    protected val base64 = BaseEncoding.base64()

    override fun handleBinaryMessage(session: WebSocketSession, message: BinaryMessage) {
        handleIncomming(session, message.payload.array())
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        handleIncomming(session, base64.decode(message.payload))
    }

    private fun handleIncomming(session: WebSocketSession, message: ByteArray) {
        val packet = WsPacketSerializer.deserializeWsPacket(message)
        println("message from ${session.id} - $packet")
    }

    abstract fun sendBinary(session: WebSocketSession, binary: ByteArray)

}

@Component
class ProtoWebsocketHandler : ProtoSocketHandler() {


    override fun sendBinary(session: WebSocketSession, binary: ByteArray) {
        session.sendMessage(BinaryMessage(binary))
    }
}

@Component
class ProtoSockJsHandler : ProtoSocketHandler() {


    override fun sendBinary(session: WebSocketSession, binary: ByteArray) {
        session.sendMessage(TextMessage(base64.encode(binary)))
    }
}