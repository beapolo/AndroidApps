package com.example.showtracker.data.model.showepisode

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.showtracker.data.common.Constants
import com.example.showtracker.data.model.ShowEntity

@Entity(
    tableName = Constants.TABLE_EPISODE,
    foreignKeys = [
        ForeignKey(
            entity = ShowEntity::class,
            parentColumns = [Constants.COLUMN_SHOW_ID],
            childColumns = [Constants.COLUMN_SHOW_CHILD_ID],
        )
    ]
)
data class EpisodeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.COLUMN_EPISODE_ID)
    var episodeId: Long = 0,
    @ColumnInfo(name = Constants.COLUMN_EPISODE_TITLE)
    var episodeTitle: String?,
    var number: Int?,
    var season: Int?,
    @ColumnInfo(name = Constants.COLUMN_SHOW_CHILD_ID)
    var showId: Long,
    var isWatched: Int,
)
