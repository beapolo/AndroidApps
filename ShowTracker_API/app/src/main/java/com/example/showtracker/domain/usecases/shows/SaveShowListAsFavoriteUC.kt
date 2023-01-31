package com.example.showtracker.domain.usecases.shows

import com.example.showtracker.data.ShowsRepository
import com.example.showtracker.domain.model.Show
import javax.inject.Inject

class SaveShowListAsFavoriteUC @Inject constructor(private val repository: ShowsRepository) {
    fun invoke(list: List<Show>) = repository.saveShowListAsFavorite(list)
}