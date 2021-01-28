package eu.musicnova.musicnova.scheduler.jobs

import org.quartz.*
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
/**
 * update teamspeak versions index every 12 hours
 */
class UpdateTeamspeakClientVersionsJob : Job {
    override fun execute(context: JobExecutionContext) {
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
        .withIdentity("update_client_versions_trigger","teamspeak")
        .withSchedule(
            SimpleScheduleBuilder
                .simpleSchedule()
                .repeatForever()
                .withIntervalInHours(12)
                .withMisfireHandlingInstructionFireNow()
        ).build()

    companion object {
        const val BEAN_NAME_DETAIL = "UpdateTeamspeakClientVersions_DETAIL"
        const val BEAN_NAME_TRIGGER = "UpdateTeamspeakClientVersions_TRIGGER"
    }
}