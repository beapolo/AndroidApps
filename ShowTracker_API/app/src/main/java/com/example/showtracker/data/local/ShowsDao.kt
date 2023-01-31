package com.example.showtracker.data.local

import androidx.room.*
import com.example.showtracker.data.model.entities.ShowEntity
import com.example.showtracker.data.model.entities.ShowWithSeasons
import com.example.showtracker.utils.SQLQueries

@Dao
interface ShowsDao {
    @Query(SQLQueries.SELECT_SHOW)
    suspend fun getShowWithSeasons(id: Long): ShowWithSeasons

    @Query(SQLQueries.SELECT_FAVORITE_SHOW_LIST)
    suspend fun getFavoriteShowList(): List<ShowEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun saveShow(show: ShowEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveShowListAsFavorite(showList: List<ShowEntity>): List<Long?>

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateShow(show: ShowEntity)

    @Delete
    suspend fun deleteShow(show: ShowEntity)
}