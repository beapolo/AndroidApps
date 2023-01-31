package com.example.showtracker.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.showtracker.data.common.Constants

@Entity(
    tableName = Constants.TABLE_SEASON,
    foreignKeys = [
        ForeignKey(
            entity = ShowEntity::class,
            parentColumns = [Constants.COLUMN_SHOW_ID],
            childColumns = [Constants.COLUMN_SHOW_CHILD_ID],
        )
    ]
)
data class SeasonEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.COLUMN_SEASON_ID)
    var id: Long = 0,
    var number: Int? = null,
    var overview: String? = null,
    @ColumnInfo(name = Constants.COLUMN_EPISODE_COUNT)
    var episodeCount: Int? = null,
    @ColumnInfo(name = Constants.COLUMN_SHOW_CHILD_ID)
    var showId: Long,
)
