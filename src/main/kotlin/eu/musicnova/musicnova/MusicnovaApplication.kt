package eu.musicnova.musicnova

import eu.musicnova.api.Module
import eu.musicnova.musicnova.boot.MusicnovaCommandlineBoot
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling
import picocli.CommandLine
import kotlin.system.exitProcess

//eu.musicnova.musicnova
@SpringBootApplication(

)
@EnableScheduling
class MusicnovaApplication:Module {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            MusicnovaCommandlineBoot.accept(args)
        }
    }
}
