package com.example.showtracker.ui.actors

import com.example.showtracker.domain.model.*

data class ActorState(
    val actorList: List<Actor> = ArrayList(),
    val message: String? = null,
    val messageWithUndo: String? = null,
)