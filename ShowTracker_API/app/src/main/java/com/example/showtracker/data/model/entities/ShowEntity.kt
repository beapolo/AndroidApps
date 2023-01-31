package com.example.showtracker.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.showtracker.data.common.Constants

@Entity(tableName = Constants.TABLE_SHOW)
data class ShowEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.COLUMN_SHOW_ID)
    var showId: Long,
    @ColumnInfo(name = Constants.COLUMN_SHOW_TITLE)
    var showTitle: String?,
    var genre: String?,
    @ColumnInfo(name = Constants.COLUMN_STREAMING_SERVICE)
    var streamingService: String?,
    @ColumnInfo(name = Constants.COLUMN_FAVORITE)
    var isFavorite: Int,
    var img: String?,
    var overview: String?
)
