package com.example.showtracker.data

import androidx.room.withTransaction
import com.example.showtracker.data.model.showactor.ShowActorCrossRef
import com.example.showtracker.data.model.toActor
import com.example.showtracker.data.model.toActorEntity
import com.example.showtracker.data.room.TrackerRoomDatabase
import com.example.showtracker.domain.model.Actor
import javax.inject.Inject

class ActorsRepository @Inject constructor(private val room: TrackerRoomDatabase) {
    suspend fun getActors() = room.showActorDao().getActorListWithShows().map { it.toActor() }

    suspend fun getActorByShow(id: Long) =
        room.showActorDao().getActorListWithShows(id).map { it.toActor() }

    suspend fun insertActor(actor: Actor): Long {
        val id = room.actorsDao().insertActor(actor.toActorEntity())
        actor.id = id
        return id
    }

    suspend fun insertActorListByShowId(list: List<Actor>) {
        room.withTransaction {
            list.forEach { actor ->
                room.actorsDao().insertActor(actor.toActorEntity())
                actor.showList.forEach { show ->
                    room.showActorDao().insertShowActor(ShowActorCrossRef(actor.id, show.id))
                }
            }
        }
    }

    suspend fun deleteActor(actor: Actor) = room.actorsDao().deleteActor(actor.toActorEntity())

    suspend fun deleteActorList(list: List<Actor>) {
        room.withTransaction {
            list.forEach {
                room.showActorDao().deleteShowActorListByActor(it.id)
                room.actorsDao().deleteActor(it.toActorEntity())
            }
        }
    }
}