package com.example.showtracker.domain.usecases.shows

import com.example.showtracker.data.ShowsRepository
import javax.inject.Inject

class GetShowDetailsUC @Inject constructor(private val repository: ShowsRepository) {
    fun invoke(id: Long) = repository.getShowDetails(id)
}