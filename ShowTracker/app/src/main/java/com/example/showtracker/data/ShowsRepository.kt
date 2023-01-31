package com.example.showtracker.data

import androidx.room.withTransaction
import com.example.showtracker.data.model.*
import com.example.showtracker.data.model.showactor.ShowActorCrossRef
import com.example.showtracker.data.room.TrackerRoomDatabase
import com.example.showtracker.domain.model.*
import javax.inject.Inject

class ShowsRepository @Inject constructor(private val room: TrackerRoomDatabase) {

    suspend fun getShows() = room.showsDao().getShows().map { it.toShow() }

    suspend fun getShowWithEpisodes(id: Long) = room.showEpisodesDao().getShow(id).toShow()

    suspend fun insertShow(show: Show): Long {
        val id = room.showsDao().insertShow(show.toShowEntity())
        show.id = id
        return id
    }

    suspend fun insertShowList(list: ArrayList<Show>) {
        room.withTransaction {
            room.showsDao().insertShowList(list.map { it.toShowEntity() })
            list.forEach {
                it.episodeList.forEach { episode ->
                    room.episodesDao().insertEpisode(episode.toEpisodeEntity())
                }
            }

            list.forEach { show ->
                show.actorList.forEach { actor ->
                    val actorId = room.actorsDao().insertActor(actor.toActorEntity())
                    room.showActorDao().insertShowActor(ShowActorCrossRef(actorId, show.id))
                }
            }
        }
    }

    suspend fun updateShowList(list: List<Show>) {
        room.withTransaction {
            list.forEach {
                room.showsDao().update(it.toShowEntity())
            }
        }
    }

    suspend fun deleteShow(show: Show) = room.showsDao().deleteShow(show.toShowEntity())

    suspend fun deleteShowList(list: List<Show>) {
        room.withTransaction {
            list.forEach {
                room.episodesDao().deleteEpisodeListByShow(it.id)
                room.showActorDao().deleteShowActorListByShow(it.id)
                room.showsDao().deleteShow(it.toShowEntity())
            }
        }
    }
}