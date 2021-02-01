package eu.musicnova.musicnova.tasks

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class TaskManager(
    private val postBootRunner: List<PostBootRunner>
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) = runBlocking(Dispatchers.Default) {
        postBootRunner.forEach { postBootRunner ->
            launch { postBootRunner.runPostBoot()}
        }
    }
}