package com.example.showtracker.domain.usecases.episodes

import com.example.showtracker.data.EpisodesRepository
import com.example.showtracker.data.ShowsRepository
import com.example.showtracker.domain.model.Episode
import javax.inject.Inject

class InsertEpisodeUC @Inject constructor(
    private val repository: EpisodesRepository,
    private val showsRep: ShowsRepository
) {
    suspend fun invoke(episode: Episode): Long {
        val show = showsRep.getShowWithEpisodes(episode.show.id)
        val seasonEpisodes = show.episodeList.filter { it.season == episode.season }
        val result: Long =
            if (episode.title == null || episode.season == null || episode.number == null) {
                com.example.showtracker.domain.model.Error.FILL_OPTIONS.error
            } else if (show.episodeList.map { it.title?.lowercase() }
                    .contains(episode.title?.lowercase())) {
                com.example.showtracker.domain.model.Error.REPEATED_NAME.error
            } else if (seasonEpisodes.map { it.number }.contains(episode.number)) {
                com.example.showtracker.domain.model.Error.WRONG_EPISODE_NUMBER.error
            } else {
                repository.insertEpisode(episode)
            }
        return result
    }
}