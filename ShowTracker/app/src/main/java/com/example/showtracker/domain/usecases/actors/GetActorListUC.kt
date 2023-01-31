package com.example.showtracker.domain.usecases.actors

import com.example.showtracker.data.ActorsRepository
import javax.inject.Inject

class GetActorListUC @Inject constructor(private val repository: ActorsRepository) {
    suspend fun invoke() = repository.getActors()
}