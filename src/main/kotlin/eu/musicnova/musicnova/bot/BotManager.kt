package eu.musicnova.musicnova.bot

import eu.musicnova.api.Bot
import eu.musicnova.musicnova.tasks.PostBootRunner
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.annotation.PreDestroy

@Component
class BotManager : PostBootRunner {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val bots = mutableListOf<Bot>()
    override suspend fun runPostBoot() {
        logger.info("BotManger Started...")
    }

    @PreDestroy
    fun onBotMangerStop() {
        bots.forEach { bot ->
            bot.close()
        }
    }
}