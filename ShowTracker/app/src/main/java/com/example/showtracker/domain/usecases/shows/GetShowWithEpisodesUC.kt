package com.example.showtracker.domain.usecases.shows

import com.example.showtracker.data.ShowsRepository
import javax.inject.Inject

class GetShowWithEpisodesUC @Inject constructor(private val repository: ShowsRepository) {
    suspend fun invoke(id: Long) = repository.getShowWithEpisodes(id)
}