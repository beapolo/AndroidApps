package com.example.showtracker.domain.usecases.actors

import com.example.showtracker.data.ActorsRepository
import com.example.showtracker.domain.model.Actor
import javax.inject.Inject

class DeleteActorUC @Inject constructor(val repository: ActorsRepository) {
    suspend fun invoke(actor: Actor) = repository.deleteActor(actor)
}