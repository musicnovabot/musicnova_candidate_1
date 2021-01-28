package eu.musicnova.musicnova.web

import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class DefautlDashboardPagesController {

    @GetMapping("/")
    fun setDashboard(model: Model): String {
        model["content"] = "Replaced Test Content"

        return "dashboard"
    }

    @GetMapping("/login")
    fun setLoginPage(model: Model): String {
        val auth = SecurityContextHolder.getContext().authentication
        return if (auth == null || auth is AnonymousAuthenticationToken) "login" else "redirect:/"
    }
}