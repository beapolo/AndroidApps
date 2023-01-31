package com.example.showtracker.domain.usecases.actors

import com.example.showtracker.data.ActorsRepository
import javax.inject.Inject

class GetActorsBySeasonUC @Inject constructor(private val repository: ActorsRepository) {
    fun invoke(tvId: Long, seasonNumber: Int) = repository.getActorsBySeason(tvId, seasonNumber)
}