package eu.musicnova.api

import eu.musicnova.api.event.Event
import java.io.Closeable

interface BotEventListener : Closeable {
    var onEvent: Event.() -> Unit
    override fun close()
}