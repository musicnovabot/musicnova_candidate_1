package eu.musicnova.musicnova

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker

@SpringBootApplication
@EnableScheduling
class MusicnovaApplication

fun main(args: Array<String>) {
    runApplication<MusicnovaApplication>(*args)

}
