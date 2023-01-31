package com.example.showtracker.data.model.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.example.showtracker.data.common.Constants

data class ShowWithSeasons(
    @Embedded val show: ShowEntity,
    @Relation(
        parentColumn = Constants.COLUMN_SHOW_ID,
        entityColumn = Constants.COLUMN_SHOW_CHILD_ID
    )
    val seasons: List<SeasonEntity> = ArrayList()
)
