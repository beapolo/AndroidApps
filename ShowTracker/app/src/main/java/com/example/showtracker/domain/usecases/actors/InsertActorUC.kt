package com.example.showtracker.domain.usecases.actors

import com.example.showtracker.data.ActorsRepository
import com.example.showtracker.domain.model.Actor
import com.example.showtracker.ui.common.Constants
import javax.inject.Inject

class InsertActorUC @Inject constructor(
    private val repository: ActorsRepository
) {
    suspend fun invoke(actor: Actor): Long {
        val result: Long =
            if (actor.name == Constants.EMPTY || actor.age == null) {
                com.example.showtracker.domain.model.Error.FILL_OPTIONS.error
            } else {
                repository.insertActor(actor)
            }

        return result
    }
}