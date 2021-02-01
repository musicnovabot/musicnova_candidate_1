package eu.musicnova.musicnova.scheduler.jobs

import com.coreoz.windmill.Windmill
import com.coreoz.windmill.files.FileSource
import eu.musicnova.musicnova.database.TeamspeakClientVersionDatabase
import eu.musicnova.musicnova.database.TeamspeakClientVersionJPA
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import org.quartz.*
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.Example
import org.springframework.stereotype.Component
import java.io.InputStream
import kotlin.streams.toList


@Component
/**
 * update teamspeak versions index every 12 hours
 */
class UpdateTeamspeakClientVersionsJob(
    val client: HttpClient,
    val database: TeamspeakClientVersionDatabase
) : Job {


    override fun execute(context: JobExecutionContext) = runBlocking {
        //TODO("logging")
        client.get<InputStream>("https://raw.githubusercontent.com/ReSpeak/tsdeclarations/master/Versions.csv")
            .use { stream ->
                val rowStream = Windmill.parse(FileSource.of(stream))
                val filteredRowStream = rowStream.skip(1).filter { row ->
                    val version = row.cell("version").asString()
                    val platform = row.cell("platform").asString()
                    val hash = row.cell("hash").asString()

                    !database.existsByVersionAndPlatformAndHash(version, platform, hash)
                }
                val newObjects = filteredRowStream.map { row ->
                    val version = row.cell("version").asString()
                    val platform = row.cell("platform").asString()
                    val hash = row.cell("hash").asString()
                    TeamspeakClientVersionJPA().apply {
                        this.version = version
                        this.platform = platform
                        this.hash = hash
                        this.versionWithoutBuildID = version.split(" ",limit = 2).first()
                    }
                }
                database.saveAll(newObjects.toList())
            }


        println("update teamspeak versions here")
    }


    @Bean
    @Qualifier(BEAN_NAME_DETAIL)
    fun detail(): JobDetail = JobBuilder.newJob()
        .ofType(UpdateTeamspeakClientVersionsJob::class.java)
        .storeDurably()
        .withIdentity("update_client_versions", "teamspeak")
        .build()

    @Bean
    @Qualifier(BEAN_NAME_TRIGGER)
    fun trigger(@Qualifier(BEAN_NAME_DETAIL) detail: JobDetail): Trigger = TriggerBuilder.newTrigger()
        .forJob(detail)
        .withIdentity("update_client_versions_trigger", "teamspeak")
        .withSchedule(
            SimpleScheduleBuilder
                .simpleSchedule()
                .repeatForever()
                .withIntervalInHours(12)
                //.withIntervalInSeconds(10)
                .withMisfireHandlingInstructionFireNow()
        ).build()

    companion object {
        const val BEAN_NAME_DETAIL = "UpdateTeamspeakClientVersions_DETAIL"
        const val BEAN_NAME_TRIGGER = "UpdateTeamspeakClientVersions_TRIGGER"
    }
}