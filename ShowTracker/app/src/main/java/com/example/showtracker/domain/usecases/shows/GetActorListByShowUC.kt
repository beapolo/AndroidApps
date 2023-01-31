package com.example.showtracker.domain.usecases.shows

import com.example.showtracker.data.ActorsRepository
import javax.inject.Inject

class GetActorListByShowUC @Inject constructor(private val repository: ActorsRepository) {
    suspend fun invoke(id: Long) = repository.getActorByShow(id)
}