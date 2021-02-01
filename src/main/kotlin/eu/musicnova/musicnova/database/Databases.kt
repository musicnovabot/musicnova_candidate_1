package eu.musicnova.musicnova.database

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BotDatabase : JpaRepository<BotJPA, UUID> {
    fun getAllByName(name: String): List<BotJPA>
    fun getByName(name: String): BotJPA?
    fun getById(id: UUID): BotJPA?
}

interface TeamspeakIdentityDatabase:JpaRepository<TeamspeakBotIdentityJPA,UUID>

interface TeamspeakClientVersionDatabase : JpaRepository<TeamspeakClientVersionJPA, Long> {
    fun existsByVersionAndPlatformAndHash(version: String, platform: String, hash: String): Boolean

}