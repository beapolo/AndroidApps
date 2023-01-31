package com.example.showtracker.data.model.showactor

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.showtracker.data.common.Constants
import com.example.showtracker.data.model.ShowEntity


data class ShowWithActors(
    @Embedded val show: ShowEntity,
    @Relation(
        parentColumn = Constants.COLUMN_SHOW_ID,
        entityColumn = Constants.COLUMN_ACTOR_ID,
        associateBy = Junction(ShowActorCrossRef::class)
    )
    val actors: List<ActorEntity>
)
