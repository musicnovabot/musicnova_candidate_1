package eu.musicnova.musicnova.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PwaController {

    @GetMapping("/manifest.json")
    fun getManifstJsoN(): PwaManifestJSONData {

        return PwaManifestJSONData(
            "Musicnova",
            "mn",
            "#000000",
            "#000000",
            PwaManifestJSONData.DisplayMode.BROWSER,
            null,
            "/",
            "/"
        )
    }
}