package org.greenrobot.eventbus

import eu.musicnova.musicnova.MainThreadRunner


private object MusicnovaMainThreadSupport : MainThreadSupport {
    override fun isMainThread(): Boolean = Thread.currentThread().name == "main"

    override fun createPoster(eventBus: EventBus):Poster = PosterImpl(eventBus)


}
private class PosterImpl(
    val eventBus: EventBus
) : Poster {
    override fun enqueue(subscription: Subscription, event: Any) {
        val post = PendingPost.obtainPendingPost(subscription, event);
        MainThreadRunner.execute {
            eventBus.invokeSubscriber(post)
        }
    }
}
@JvmField
val musicnovaMainThreadSupport: MainThreadSupport = MusicnovaMainThreadSupport
private val mainThreadSupportField by lazy {
    EventBusBuilder::class.java.getDeclaredField("mainThreadSupport").also {
        it.isAccessible = true
    }
}


internal fun EventBusBuilder.setMainMusicnovaThreadSupport() = setMainThreadSupport(musicnovaMainThreadSupport)
internal fun EventBusBuilder.setMainThreadSupport(mainThreadSupport: MainThreadSupport): EventBusBuilder {
    mainThreadSupportField.set(this, mainThreadSupport)
    return this
}
