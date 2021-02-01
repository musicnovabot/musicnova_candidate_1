package eu.musicnova.musicnova.web


import com.fasterxml.jackson.annotation.JsonProperty
import java.awt.ComponentOrientation

data class PwaManifestJSONData(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("short_name")
    val shortName: String,
    @JsonProperty("background_color")
    val backgroundColor: String,
    @JsonProperty("theme_color")
    val themeColor: String,
    @JsonProperty("display")
    val display: DisplayMode,
    @JsonProperty("orientation")
    val orientation: String?,
    @JsonProperty("scope")
    val scope: String,
    @JsonProperty("start_url")
    val startURL: String

) {
    enum class DisplayMode(private val text: String) {
        BROWSER("browser"),
        STANDALONE("standalone"),
        MINIMAL_UI("minimal-ui"),
        FULLSCREEN("fullscreen");

        override fun toString(): String = text
    }
}