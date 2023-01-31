package com.example.showtracker.domain.usecases.shows

import com.example.showtracker.data.ShowsRepository
import javax.inject.Inject

class GetPopularShowListUC @Inject constructor(private val repository: ShowsRepository) {
    fun invoke() = repository.getPopularShowList()
}