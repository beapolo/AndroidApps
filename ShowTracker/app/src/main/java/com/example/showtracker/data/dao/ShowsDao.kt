package com.example.showtracker.data.dao

import androidx.room.*
import com.example.showtracker.data.model.ShowEntity
import com.example.showtracker.utils.SQLQueries

@Dao
interface ShowsDao {
    @Query(SQLQueries.SELECT_SHOW_LIST)
    suspend fun getShows(): List<ShowEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertShowList(show: List<ShowEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertShow(show: ShowEntity): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(show: ShowEntity)

    @Delete
    suspend fun deleteShow(show: ShowEntity)
}