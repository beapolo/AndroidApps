package com.example.showtracker.data.model.showepisode

import androidx.room.Embedded
import androidx.room.Relation
import com.example.showtracker.data.common.Constants
import com.example.showtracker.data.model.ShowEntity

data class ShowWithEpisodes(
    @Embedded val show: ShowEntity,
    @Relation(
        parentColumn = Constants.COLUMN_SHOW_ID,
        entityColumn = Constants.COLUMN_SHOW_CHILD_ID
    )
    val episodes: List<EpisodeEntity> = ArrayList()
)
