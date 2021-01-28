package eu.musicnova.musicnova.web

import org.springframework.stereotype.Component
import org.springframework.web.socket.config.annotation.*


@Component
@EnableWebSocket
class SocketConfiguration(
    private val sockJsHandler: ProtoSockJsHandler,
    private val wsHandler:ProtoWebsocketHandler
) : WebSocketConfigurer {


    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(wsHandler, "/internal/socket")
        registry.addHandler(sockJsHandler, "/internal/socket").withSockJS()
    }

}