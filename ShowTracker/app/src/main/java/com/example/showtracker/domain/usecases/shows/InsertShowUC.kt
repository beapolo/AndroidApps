package com.example.showtracker.domain.usecases.shows

import com.example.showtracker.R
import com.example.showtracker.data.ShowsRepository
import com.example.showtracker.domain.model.Show
import com.example.showtracker.utils.StringProvider
import javax.inject.Inject

class InsertShowUC @Inject constructor(
    private val stringProvider: StringProvider,
    private val repository: ShowsRepository
) {
    suspend fun invoke(show: Show): Long {
        val shows = repository.getShows()
        val result: Long =
            if (show.genre == null
                || show.streamingService == null
                || show.title == stringProvider.getString(R.string.empty)
            ) {
                com.example.showtracker.domain.model.Error.FILL_OPTIONS.error
            } else if (shows.map { it.title?.lowercase() }
                    .contains(show.title?.lowercase())) {
                com.example.showtracker.domain.model.Error.REPEATED_NAME.error
            } else {
                repository.insertShow(show)
            }
        return result
    }
}