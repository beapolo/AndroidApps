package com.example.showtracker.data

import androidx.room.withTransaction
import com.example.showtracker.data.common.Constants
import com.example.showtracker.data.model.entities.toShow
import com.example.showtracker.data.model.entities.toShowEntity
import com.example.showtracker.data.remote.ShowRemoteRepository
import com.example.showtracker.data.local.room.LocalDatabase
import com.example.showtracker.data.model.entities.toSeasonEntity
import com.example.showtracker.domain.model.*
import com.example.showtracker.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class ShowsRepository @Inject constructor(
    private val localDatabase: LocalDatabase,
    private val remoteRepository: ShowRemoteRepository
) {

    fun getFavoriteShowList(): Flow<NetworkResult<List<Show>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = NetworkResult.Success(
                localDatabase.showsDao().getFavoriteShowList().map { it.toShow() })
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getPopularShowList(): Flow<NetworkResult<List<Show?>?>> {
        return flow {
            emit(NetworkResult.Loading())

            val result = remoteRepository.getPopularShowList()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getShowDetails(id: Long): Flow<NetworkResult<Show?>> {
        return flow {
            val localShow = getShowWithSeasons(id)
            emit(NetworkResult.Success(localShow))
            emit(NetworkResult.Loading())

            val result = remoteRepository.getShowDetails(id)
            if (result is NetworkResult.Success) {
                result.data?.let { remoteShow -> updateLocalDataBase(remoteShow, localShow) }
            } else {
                result.data = localShow
            }

            emit(result)
        }
    }

    private suspend fun getShowWithSeasons(id: Long): Show? {
        return try {
            localDatabase.showsDao().getShowWithSeasons(id).toShow()
        } catch (e: Exception) {
            Timber.e(e.message, e)
            null
        }
    }

    private suspend fun updateLocalDataBase(remoteShow: Show, localShow: Show?) {
        try {
            localDatabase.withTransaction {
                localDatabase.seasonsDao().deleteSeasonListByShow(remoteShow.id)
                localDatabase.showsDao().deleteShow(remoteShow.toShowEntity())

                if (localShow != null) {
                    remoteShow.isFavorite = localShow.isFavorite
                }
                val showId = localDatabase.showsDao().saveShow(remoteShow.toShowEntity())
                remoteShow.seasonList.forEach { season ->
                    season?.show = Show(showId)
                    season?.toSeasonEntity()
                        ?.let { existingSeason ->
                            localDatabase.seasonsDao().saveSeason(existingSeason)
                        }
                }
            }
        } catch (e: Exception) {
            Timber.e(e.message, e)
        }
    }

    fun saveShowListAsFavorite(list: List<Show>): Flow<NetworkResult<List<Long?>>> {
        return flow {
            val savedList = saveFavoriteListInDataBase(list)
            val result: NetworkResult<List<Long?>> =
                if (savedList.isEmpty()) {
                    NetworkResult.Error(Constants.FAVORITE_FAILED, savedList)
                } else {
                    NetworkResult.Success(savedList)
                }
            emit(result)
        }
    }

    private suspend fun saveFavoriteListInDataBase(list: List<Show>): List<Long?> {
        val result: List<Long?> =
            try {
                localDatabase.showsDao().saveShowListAsFavorite(list.map { it.toShowEntity() })
            } catch (e: Exception) {
                Timber.e(e.message, e)
                emptyList()
            }
        return result
    }

    fun removeFromFavoriteShowList(list: List<Show>): Flow<NetworkResult<Unit>> {
        return flow {
            val result = try {
                NetworkResult.Success(
                    localDatabase.withTransaction {
                        list.forEach {
                            localDatabase.showsDao().updateShow(it.toShowEntity())
                        }
                    })
            } catch (e: Exception) {
                Timber.e(e.message, e)
                NetworkResult.Error(Constants.UNFAVORITE_FAILED)
            }
            emit(result)
        }
    }
}