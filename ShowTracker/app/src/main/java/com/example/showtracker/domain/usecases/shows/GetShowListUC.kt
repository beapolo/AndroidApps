package com.example.showtracker.domain.usecases.shows

import com.example.showtracker.data.ShowsRepository
import javax.inject.Inject

class GetShowListUC @Inject constructor(private val repository: ShowsRepository) {
    suspend fun invoke() = repository.getShows()
}