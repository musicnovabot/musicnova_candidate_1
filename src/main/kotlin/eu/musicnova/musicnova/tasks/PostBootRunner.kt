package eu.musicnova.musicnova.tasks

interface PostBootRunner {
    suspend fun runPostBoot()
}