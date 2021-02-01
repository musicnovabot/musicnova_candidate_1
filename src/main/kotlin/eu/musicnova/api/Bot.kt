package eu.musicnova.api

import java.io.Closeable

interface Bot : Closeable {
    val isConnected: Boolean
    suspend fun connect()
    suspend fun disconnect()

    override fun close()
}