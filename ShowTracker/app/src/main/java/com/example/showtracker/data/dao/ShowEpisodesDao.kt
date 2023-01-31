package com.example.showtracker.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.showtracker.data.model.showepisode.ShowWithEpisodes
import com.example.showtracker.utils.SQLQueries

@Dao
interface ShowEpisodesDao {
    @Transaction
    @Query(SQLQueries.SELECT_SHOW_WITH_EPISODES)
    suspend fun getShow(id: Long): ShowWithEpisodes
}