package com.example.showtracker.domain.usecases.shows

import com.example.showtracker.data.ShowsRepository
import javax.inject.Inject

class GetFavoriteShowListUC @Inject constructor(private val repository: ShowsRepository) {
    fun invoke() = repository.getFavoriteShowList()
}