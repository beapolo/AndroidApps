package com.example.showtracker.ui.actors

import com.example.showtracker.domain.model.Actor

sealed class ActorEvent {
    object GetAll : ActorEvent()
    class GetByShow(val showId: Long) : ActorEvent()
    class InsertActorList(val actorList: List<Actor>, val showId: Long) : ActorEvent()
    class DeleteActorList(val actorList: List<Actor>, val showId: Long) : ActorEvent()
    object MessageShown : ActorEvent()
}