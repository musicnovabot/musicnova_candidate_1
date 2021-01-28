---
title: eu.musicnova.musicnova.web -
---
//[musicnova](../index.md)/[eu.musicnova.musicnova.web](index.md)



# Package eu.musicnova.musicnova.web  


## Types  
  
|  Name|  Summary| 
|---|---|
| <a name="eu.musicnova.musicnova.web/BotPagesController///PointingToDeclaration/"></a>[BotPagesController](-bot-pages-controller/index.md)| <a name="eu.musicnova.musicnova.web/BotPagesController///PointingToDeclaration/"></a>[jvm]  <br>Content  <br>@Controller()  <br>  <br>class [BotPagesController](-bot-pages-controller/index.md)  <br><br><br>
| <a name="eu.musicnova.musicnova.web/DefautlDashboardPagesController///PointingToDeclaration/"></a>[DefautlDashboardPagesController](-defautl-dashboard-pages-controller/index.md)| <a name="eu.musicnova.musicnova.web/DefautlDashboardPagesController///PointingToDeclaration/"></a>[jvm]  <br>Content  <br>@Controller()  <br>  <br>class [DefautlDashboardPagesController](-defautl-dashboard-pages-controller/index.md)  <br><br><br>
| <a name="eu.musicnova.musicnova.web/ProtoSocketHandler///PointingToDeclaration/"></a>[ProtoSocketHandler](-proto-socket-handler/index.md)| <a name="eu.musicnova.musicnova.web/ProtoSocketHandler///PointingToDeclaration/"></a>[jvm]  <br>Content  <br>abstract class [ProtoSocketHandler](-proto-socket-handler/index.md) : AbstractWebSocketHandler  <br><br><br>
| <a name="eu.musicnova.musicnova.web/ProtoSockJsHandler///PointingToDeclaration/"></a>[ProtoSockJsHandler](-proto-sock-js-handler/index.md)| <a name="eu.musicnova.musicnova.web/ProtoSockJsHandler///PointingToDeclaration/"></a>[jvm]  <br>Content  <br>@Component()  <br>  <br>class [ProtoSockJsHandler](-proto-sock-js-handler/index.md) : [ProtoSocketHandler](-proto-socket-handler/index.md)  <br><br><br>
| <a name="eu.musicnova.musicnova.web/ProtoWebsocketHandler///PointingToDeclaration/"></a>[ProtoWebsocketHandler](-proto-websocket-handler/index.md)| <a name="eu.musicnova.musicnova.web/ProtoWebsocketHandler///PointingToDeclaration/"></a>[jvm]  <br>Content  <br>@Component()  <br>  <br>class [ProtoWebsocketHandler](-proto-websocket-handler/index.md) : [ProtoSocketHandler](-proto-socket-handler/index.md)  <br><br><br>
| <a name="eu.musicnova.musicnova.web/SocketConfiguration///PointingToDeclaration/"></a>[SocketConfiguration](-socket-configuration/index.md)| <a name="eu.musicnova.musicnova.web/SocketConfiguration///PointingToDeclaration/"></a>[jvm]  <br>Content  <br>@Component()  <br>@EnableWebSocket()  <br>  <br>class [SocketConfiguration](-socket-configuration/index.md)(**sockJsHandler**: [ProtoSockJsHandler](-proto-sock-js-handler/index.md), **wsHandler**: [ProtoWebsocketHandler](-proto-websocket-handler/index.md)) : WebSocketConfigurer  <br><br><br>
| <a name="eu.musicnova.musicnova.web/WebAuthConfig///PointingToDeclaration/"></a>[WebAuthConfig](-web-auth-config/index.md)| <a name="eu.musicnova.musicnova.web/WebAuthConfig///PointingToDeclaration/"></a>[jvm]  <br>Content  <br>@EnableWebSecurity()  <br>  <br>class [WebAuthConfig](-web-auth-config/index.md)(**objectMapper**: ObjectMapper) : WebSecurityConfigurerAdapter  <br><br><br>

