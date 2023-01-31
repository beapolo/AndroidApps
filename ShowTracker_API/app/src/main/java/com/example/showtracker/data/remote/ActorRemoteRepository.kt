package com.example.showtracker.data.remote

import com.example.showtracker.data.model.responses.toActor
import com.example.showtracker.data.remote.api.ActorsAPI
import com.example.showtracker.domain.model.Actor
import com.example.showtracker.utils.NetworkResult
import javax.inject.Inject

class ActorRemoteRepository @Inject constructor(private val api: ActorsAPI) :
    BaseApiResponse() {
    suspend fun getActorsBySeason(tvId: Long, seasonNumber: Int): NetworkResult<List<Actor?>?> {
        return safeApiCall(apiCall = { api.getSeasonCredits(tvId.toInt(), seasonNumber) },
            transform = { list ->
                list.cast?.map { it?.toActor() }
            })
    }

}