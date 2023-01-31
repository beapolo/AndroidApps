package com.example.showtracker.domain.usecases.actors

import com.example.showtracker.data.ActorsRepository
import com.example.showtracker.domain.model.Actor
import javax.inject.Inject

class DeleteActorListUC @Inject constructor(val repository: ActorsRepository) {
    suspend fun invoke(list: List<Actor>) = repository.deleteActorList(list)
}