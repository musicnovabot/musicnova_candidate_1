@file:Suppress("JpaDataSourceORMInspection")

package eu.musicnova.musicnova.database

import java.util.*
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "bot")
open class BotJPA {
    @Id
    @Column(length = 16)
    open var id: UUID = UUID.randomUUID()

    open var name = "Bot"

    @Column()
    open var connected = false

}

@Entity
@Table(name = "bot_discord")
open class DiscordBOTJPA : BotJPA() {

    @ManyToOne(optional = true)
    open var identity: DiscordBotIdentityJPA? = null

    @ManyToOne
    open lateinit var guild: DiscordGuildJPA
}

@Entity
@Table(name = "bot_teamspeak")
open class TeamspeakBotJPA : BotJPA() {

    @ManyToOne()
    open lateinit var tsIdentity: TeamspeakBotIdentityJPA
}


@Entity
@Table(name = "identity")
@Inheritance(strategy = InheritanceType.JOINED)
open class BotIdentityJPA {


    @Id
    @Column(length = 16)
    open var id: UUID = UUID.randomUUID()

    @Column(nullable = true)
    open var name: String? = null

}

@Entity
@Table(name = "identity_discord")
open class DiscordBotIdentityJPA : BotIdentityJPA() {

    @Lob
    open var token: String = ""
}

@Entity
@Table(name = "identity_teamspeak")
open class TeamspeakBotIdentityJPA : BotIdentityJPA() {

    @Lob
    open lateinit var asn: ByteArray

    open var keyOffset: Long = 0

    open var lastCheckedKeyOffset: Long = 0

    companion object
}

@Entity
@Table(name = "discord_guild")
open class DiscordGuildJPA {
    @Id
    open var id: Long = 0

    @OneToMany(mappedBy = "guild")
    open lateinit var bots: MutableSet<DiscordBOTJPA>
}

@Entity
@Table(name = "teamspeak_version")
open class TeamspeakClientVersionJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open val id: Long = 0

    open lateinit var version: String
    open lateinit var platform: String

    @Column(unique = true)
    open lateinit var hash: String

    @Column()
    open lateinit var versionWithoutBuildID: String

}