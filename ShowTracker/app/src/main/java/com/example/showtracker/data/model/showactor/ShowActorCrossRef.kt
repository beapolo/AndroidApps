package com.example.showtracker.data.model.showactor

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.showtracker.data.common.Constants

@Entity(
    primaryKeys = [Constants.COLUMN_SHOW_ID, Constants.COLUMN_ACTOR_ID],
    tableName = Constants.TABLE_SHOW_ACTOR
)
data class ShowActorCrossRef(
    @ColumnInfo(name = Constants.COLUMN_ACTOR_ID)
    val actorId: Long,
    @ColumnInfo(name = Constants.COLUMN_SHOW_ID)
    val showId: Long,
)
