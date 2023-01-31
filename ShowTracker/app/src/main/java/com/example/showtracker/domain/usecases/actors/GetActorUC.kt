package com.example.showtracker.domain.usecases.actors

import com.example.showtracker.data.ActorsRepository
import javax.inject.Inject

class GetActorUC @Inject constructor(val repository: ActorsRepository) {
    suspend fun invoke(id: Long) = repository.getActorByShow(id)
}