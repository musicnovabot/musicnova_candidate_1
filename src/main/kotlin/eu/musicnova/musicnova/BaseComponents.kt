package eu.musicnova.musicnova

import eu.musicnova.api.EventManager
import eu.musicnova.musicnova.boot.CommandLineBeanPresent
import eu.musicnova.musicnova.boot.MusicnovaCommandlineBoot
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class BaseComponents {

    @Bean
    fun client() = HttpClient(OkHttp)


    @Bean
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    fun musicnovaCommandline(present: CommandLineBeanPresent): MusicnovaCommandlineBoot = present.getPresent()

    @Bean
    fun eventHandler() = EventManager.builder()
        .throwSubscriberException(false)
        //.setMainMusicnovaThreadSupport()
        .sendNoSubscriberEvent(true)
        .sendSubscriberExceptionEvent(true)
        .build()
}