package com.example.showtracker.domain.usecases.actors

import com.example.showtracker.data.ActorsRepository
import com.example.showtracker.domain.model.*
import javax.inject.Inject

class InsertActorListByShowIdUC @Inject constructor(val repository: ActorsRepository) {
    suspend fun invoke(actorList: List<Actor>) = repository.insertActorListByShowId(actorList)
}