package com.example.showtracker.ui.seasondetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.showtracker.R
import com.example.showtracker.databinding.ItemActorHorizontalBinding
import com.example.showtracker.domain.model.Actor

class ActorAdapter: ListAdapter<Actor, ActorAdapter.ActorViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ActorViewHolder(
            layoutInflater.inflate(
                R.layout.item_actor_horizontal,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }

    inner class ActorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemActorHorizontalBinding.bind(view)

        fun bind(
            actor: Actor
        ) {
            with(binding) {
                actorImg.load(actor.img)
                actorName.text = actor.name

                val characterName = actor.character
                if (characterName != null) {
                    if (characterName.contains("/")) {
                        val characters = characterName.split("/")
                        character.text = characters[0]
                    } else {
                        character.text = characterName
                    }
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Actor>() {
        override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem == newItem
        }
    }
}