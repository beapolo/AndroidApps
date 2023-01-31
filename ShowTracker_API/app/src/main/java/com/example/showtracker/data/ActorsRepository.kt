package com.example.showtracker.data

import com.example.showtracker.data.remote.ActorRemoteRepository
import com.example.showtracker.domain.model.Actor
import com.example.showtracker.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ActorsRepository @Inject constructor(
    private val remoteRepository: ActorRemoteRepository
) {

    fun getActorsBySeason(tvId: Long, seasonNumber: Int): Flow<NetworkResult<List<Actor?>?>> {
        return flow {
            emit(NetworkResult.Loading())

            val result = remoteRepository.getActorsBySeason(tvId, seasonNumber)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}