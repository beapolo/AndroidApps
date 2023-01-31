package com.example.showtracker.domain.usecases.episodes

import com.example.showtracker.data.EpisodesRepository
import javax.inject.Inject

class GetEpisodesBySeasonUC @Inject constructor(private val repository: EpisodesRepository) {
    fun invoke(tvId: Long, seasonNumber: Int) = repository.getEpisodesBySeason(tvId, seasonNumber)
}