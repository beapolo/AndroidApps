package com.example.showtracker.domain.usecases.episodes

import com.example.showtracker.data.EpisodesRepository
import com.example.showtracker.domain.model.Episode
import javax.inject.Inject

class UpdateEpisodeUC @Inject constructor(val repository: EpisodesRepository) {
    suspend fun invoke(episode: Episode) = repository.updateEpisode(episode)
}