package com.example.showtracker.data.model.showactor

import androidx.room.*
import com.example.showtracker.data.common.Constants
import com.example.showtracker.data.model.ShowEntity

data class ActorWithShows(
    @Embedded val actor: ActorEntity,
    @Relation(
        parentColumn = Constants.COLUMN_ACTOR_ID,
        entityColumn = Constants.COLUMN_SHOW_ID,
        associateBy = Junction(ShowActorCrossRef::class)
    )
    val shows: List<ShowEntity>
)
