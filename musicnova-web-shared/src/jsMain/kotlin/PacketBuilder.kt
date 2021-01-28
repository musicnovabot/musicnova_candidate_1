import eu.musicnova.webshared.PacketPlayerUpdateTrack
import kotlin.js.JsName

object PacketBuilder {

    @JsName("buildPacketPlayerUpdateTrack")
    fun buildPacketPlayerUpdateTrack(title: String, author: String? = null) =
        PacketPlayerUpdateTrack(title, author)
}