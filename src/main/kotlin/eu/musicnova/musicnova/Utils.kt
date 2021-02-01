package eu.musicnova.musicnova

import com.github.manevolent.ts3j.identity.LocalIdentity
import com.github.manevolent.ts3j.util.Ts3Crypt
import eu.musicnova.musicnova.database.TeamspeakBotIdentityJPA
import org.slf4j.LoggerFactory
import java.util.*

fun TeamspeakBotIdentityJPA.Companion.fromLocalIdentity(
    localIdentity: LocalIdentity,
    id: UUID? = null,
    name: String? = null
): TeamspeakBotIdentityJPA = TeamspeakBotIdentityJPA().apply {
    this.asn = localIdentity.toASN()
    this.keyOffset = localIdentity.keyOffset
    this.lastCheckedKeyOffset = localIdentity.lastCheckedKeyOffset
    if (id != null) {
        this.id = id
    }
    if (name != null) {
        this.name = name
    }
}


fun TeamspeakBotIdentityJPA.toLocalIdentity(): LocalIdentity = Ts3Crypt.loadIdentityFromAsn(asn).also { localIdentity ->
    localIdentity.keyOffset = keyOffset
    localIdentity.lastCheckedKeyOffset = lastCheckedKeyOffset
}

inline fun <reified T> T.logger() = LoggerFactory.getLogger(T::class.java)
inline fun <reified T> logger() = LoggerFactory.getLogger(T::class.java)