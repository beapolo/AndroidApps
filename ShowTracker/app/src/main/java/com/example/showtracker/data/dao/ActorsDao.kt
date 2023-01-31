package com.example.showtracker.data.dao

import androidx.room.*
import com.example.showtracker.data.model.showactor.ActorEntity

@Dao
interface ActorsDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertActor(actor: ActorEntity): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateActor(actor: ActorEntity)

    @Delete
    suspend fun deleteActor(actor: ActorEntity)
}