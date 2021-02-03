package eu.musicnova.musicnova

import kotlinx.coroutines.MainCoroutineDispatcher
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue
import kotlin.coroutines.CoroutineContext


object MainThreadRunner : Runnable, Executor, MainCoroutineDispatcher() {
    private val logger = logger()
    private val queue = LinkedBlockingQueue<Pair<Runnable, CoroutineContext?>>()
    override fun run() {
        logger.info("Main EventLoop Started...")
        while (true) {
            val pair = queue.take()
            val runnable = pair.first
            val context = pair.second
            try {
                runnable.run()
            } catch (e: Throwable) {
                logger.error("Task in MainLoop failed", e, context)
            }
        }
    }


    override val immediate: MainCoroutineDispatcher = this

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        queue.add(Pair(block, context))
    }

    override fun execute(command: Runnable) {
        queue.add(Pair(command, null))
    }
}