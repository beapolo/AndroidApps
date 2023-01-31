package com.example.showtracker.domain.usecases.shows

import com.example.showtracker.data.ShowsRepository
import com.example.showtracker.domain.model.Show
import javax.inject.Inject

class DeleteShowListUC @Inject constructor(private val repository: ShowsRepository) {
    suspend fun invoke(list: List<Show>) = repository.deleteShowList(list)
}