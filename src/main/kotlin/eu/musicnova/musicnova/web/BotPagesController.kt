package eu.musicnova.musicnova.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class BotPagesController {

    @GetMapping("/bot/")
    fun getBot(model: Model): String {
        model.set("content", "test bot content")
        return "bot-dashboard"
    }

}