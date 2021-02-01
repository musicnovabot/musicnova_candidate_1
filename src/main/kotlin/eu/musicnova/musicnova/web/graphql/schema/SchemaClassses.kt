package eu.musicnova.musicnova.web.graphql.schema

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.annotations.GraphQLName
import com.github.manevolent.ts3j.identity.LocalIdentity
import com.google.common.io.BaseEncoding
import eu.musicnova.musicnova.database.BotDatabase
import eu.musicnova.musicnova.database.TeamspeakBotIdentityJPA
import eu.musicnova.musicnova.database.TeamspeakIdentityDatabase
import eu.musicnova.musicnova.fromLocalIdentity

import org.springframework.stereotype.Component
import java.util.*

interface GraphQLDefinition
interface GraphQLQueryDefinition : GraphQLDefinition
interface GraphQLMutationDefinition : GraphQLDefinition

@Component
class MusicnovaCommonBotQuery(
    val commonBotDatabase: BotDatabase
) : GraphQLQueryDefinition {

    @GraphQLDescription("im a test hey... greet me!")
    fun hey(): String? = "oh hey mark"

    fun allBots() =
        commonBotDatabase.findAll().map { jpa -> GraphQLCommonBot(jpa.id.toString(), jpa.name, jpa.connected) }

    @GraphQLName("bot")
    fun bot(id: String): GraphQLCommonBot? =
        commonBotDatabase.getById(UUID.fromString(id))?.let { botJPA ->
            GraphQLCommonBot(botJPA.toString(), botJPA.name, botJPA.connected)
        }


}

@Component
class MusicnovaBotMutatation(
    private val commonBotDatabase: BotDatabase,
    private val teamspeakMutation: MusicnovaTeamspeakMutation
) : GraphQLMutationDefinition {

    fun teamspeak() = teamspeakMutation

}

@Component
class MusicnovaTeamspeakMutation(
    private val teamspeakIdentityDatabase: TeamspeakIdentityDatabase
) {
    fun create(id: String?, name: String?): GraphQLTeamspeakBot {
        println("create teamspeak bot")
        TODO()
    }

    fun createIdentity(name: String? = null, level: Int = 8): GraphQLTeamspeakIdentity {
        val identity = LocalIdentity.generateNew(level)
        val identityJPA = TeamspeakBotIdentityJPA.fromLocalIdentity(identity, name = name)
        val identityJPASaved = teamspeakIdentityDatabase.save(identityJPA)

        return GraphQLTeamspeakIdentity(
            identityJPASaved.id.toString(),
            name,
            identity.securityLevel,
            identity.export(),
            BaseEncoding.base64().encode(identity.toASN()),
            identity.keyOffset,
            identity.lastCheckedKeyOffset,
        )
    }
}


data class GraphQLTeamspeakIdentity(
    val id: String,
    val name: String?,
    val level: Int,
    val file: String,
    val asn: String,
    val keyOffset: Long,
    val lastCheckedKeyOffset: Long
)

@GraphQLName("Bot")
open class GraphQLCommonBot(
    open val id: String,
    open val name: String,
    open val connected: Boolean
)

@GraphQLName("TeamspeakBot")
open class GraphQLTeamspeakBot(
    id: String,
    name: String,
    connected: Boolean,
    open val config: GraphQLTeamspeakBotConfig
) : GraphQLCommonBot(id, name, connected)

data class GraphQLTeamspeakBotConfig(
    val server: String
)