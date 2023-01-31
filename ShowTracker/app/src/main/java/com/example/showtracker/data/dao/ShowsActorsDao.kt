package com.example.showtracker.data.dao

import androidx.room.*
import com.example.showtracker.data.model.showactor.ActorWithShows
import com.example.showtracker.data.model.showactor.ShowActorCrossRef
import com.example.showtracker.data.model.showactor.ShowWithActors
import com.example.showtracker.utils.SQLQueries

@Dao
interface ShowsActorsDao {
    @Transaction
    @Query(SQLQueries.SELECT_ACTOR_LIST)
    suspend fun getActorListWithShows(): List<ActorWithShows>

    @Transaction
    @Query(SQLQueries.SELECT_ACTOR_LIST_WITH_SHOWS)
    suspend fun getActorListWithShows(id: Long): List<ActorWithShows>

    @Transaction
    @Query(SQLQueries.SELECT_SHOW_WITH_ACTOR)
    suspend fun getShowWithActors(id: Long): ShowWithActors

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertShowActor(item: ShowActorCrossRef)

    @Transaction
    @Query(SQLQueries.DELETE_SHOW_ACTOR_BY_SHOW)
    suspend fun deleteShowActorListByShow(id: Long)

    @Transaction
    @Query(SQLQueries.DELETE_SHOW_ACTOR_BY_ACTOR)
    suspend fun deleteShowActorListByActor(id: Long)
}