package com.example.showtracker.data

import com.example.showtracker.data.remote.EpisodesRemoteRepository
import com.example.showtracker.domain.model.Episode
import com.example.showtracker.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EpisodesRepository @Inject constructor(
    private val remoteRepository: EpisodesRemoteRepository
) {

    fun getEpisodesBySeason(tvId: Long, seasonNumber: Int): Flow<NetworkResult<List<Episode?>?>> {
        return flow {
            emit(NetworkResult.Loading())

            val result = remoteRepository.getEpisodesBySeason(tvId, seasonNumber)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}