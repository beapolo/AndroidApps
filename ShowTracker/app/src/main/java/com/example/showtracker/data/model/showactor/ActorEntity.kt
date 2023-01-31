package com.example.showtracker.data.model.showactor

import androidx.room.*
import com.example.showtracker.data.common.Constants

@Entity(
    tableName = Constants.TABLE_ACTOR,
    indices = [Index(value = [Constants.COLUMN_NAME], unique = true)]
)
data class ActorEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.COLUMN_ACTOR_ID)
    var actorId: Long,
    val name: String?,
    val age: Int?,
)
