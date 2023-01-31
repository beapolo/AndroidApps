package com.example.showtracker.data

import com.example.showtracker.data.model.toEpisodeEntity
import com.example.showtracker.data.room.TrackerRoomDatabase
import com.example.showtracker.domain.model.Episode
import javax.inject.Inject

class EpisodesRepository @Inject constructor(private val room: TrackerRoomDatabase) {
    suspend fun insertEpisode(episode: Episode): Long {
        val id = room.episodesDao().insertEpisode(episode.toEpisodeEntity())
        episode.id = id
        return id
    }

    suspend fun updateEpisode(episode: Episode) =
        room.episodesDao().updateEpisode(episode.toEpisodeEntity())

    suspend fun deleteEpisode(episode: Episode) =
        room.episodesDao().deleteEpisode(episode.toEpisodeEntity())
}