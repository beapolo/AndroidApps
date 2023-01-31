package com.example.showtracker.ui.addactor

import com.example.showtracker.domain.model.Actor

sealed class AddActorEvent{
    class Insert(val actor: Actor) : AddActorEvent()
    class Delete(val actor: Actor) : AddActorEvent()
    object MessageShown : AddActorEvent()
}
