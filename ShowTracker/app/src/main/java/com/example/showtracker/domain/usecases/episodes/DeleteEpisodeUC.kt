package com.example.showtracker.domain.usecases.episodes

import com.example.showtracker.data.EpisodesRepository
import com.example.showtracker.domain.model.Episode
import javax.inject.Inject

class DeleteEpisodeUC @Inject constructor(private val repository: EpisodesRepository) {
    suspend fun invoke(episode: Episode) = repository.deleteEpisode(episode)
}