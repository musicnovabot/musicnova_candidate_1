package eu.musicnova.api

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer

interface AudioBot : Bot {
    val audioPlayer: AudioPlayer
}