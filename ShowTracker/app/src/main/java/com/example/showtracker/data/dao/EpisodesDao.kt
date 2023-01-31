package com.example.showtracker.data.dao

import androidx.room.*
import com.example.showtracker.data.model.showepisode.EpisodeEntity
import com.example.showtracker.utils.SQLQueries

@Dao
interface EpisodesDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertEpisode(episode: EpisodeEntity): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateEpisode(episode: EpisodeEntity)

    @Delete
    suspend fun deleteEpisode(episode: EpisodeEntity)

    @Transaction
    @Query(SQLQueries.DELETE_EPISODE_LIST_BY_SHOW)
    suspend fun deleteEpisodeListByShow(id: Long)
}