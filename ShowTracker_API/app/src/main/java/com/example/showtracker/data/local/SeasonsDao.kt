package com.example.showtracker.data.local

import androidx.room.*
import com.example.showtracker.data.model.entities.SeasonEntity
import com.example.showtracker.utils.SQLQueries

@Dao
interface SeasonsDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun saveSeason(season: SeasonEntity)

    @Transaction
    @Query(SQLQueries.DELETE_SEASON_LIST_BY_SHOW)
    suspend fun deleteSeasonListByShow(id: Long)
}