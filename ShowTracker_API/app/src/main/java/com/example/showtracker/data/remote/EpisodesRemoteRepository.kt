package com.example.showtracker.data.remote

import com.example.showtracker.data.model.responses.toEpisode
import com.example.showtracker.data.remote.api.EpisodesAPI
import com.example.showtracker.domain.model.Episode
import com.example.showtracker.utils.NetworkResult
import javax.inject.Inject

class EpisodesRemoteRepository @Inject constructor(private val api: EpisodesAPI) :
    BaseApiResponse() {

    suspend fun getEpisodesBySeason(tvId: Long, seasonNumber: Int): NetworkResult<List<Episode?>?> {
        return safeApiCall(apiCall = { api.getSeasonEpisodes(tvId.toInt(), seasonNumber) },
            transform = { list ->
                list.episodes?.map { it?.toEpisode() }
            })
    }
}