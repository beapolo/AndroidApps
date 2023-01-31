package com.example.showtracker.ui.actors

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.showtracker.R
import com.example.showtracker.databinding.ItemActorBinding
import com.example.showtracker.domain.model.Actor

class ActorAdapter(
    private val actions: ActorActions,
) : ListAdapter<Actor, ActorAdapter.ActorViewHolder>(DiffCallback()) {

    interface ActorActions {
        fun onClickShow(id: Long)
        fun onStartSelectMode()
        fun itemSelected(actor: Actor)
    }

    private var selectedItems = mutableListOf<Actor>()
    fun getSelectedItems() = selectedItems.toList()

    fun resetSelectMode() {
        selectedItems.forEach { it.isSelected = false }
        selectedItems.removeAll(getSelectedItems())
        selectedMode = false
        notifyDataSetChanged()
    }

    private var selectedMode: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ActorViewHolder(layoutInflater.inflate(R.layout.item_actor, parent, false))
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }

    inner class ActorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemActorBinding.bind(view)

        fun bind(
            actor: Actor
        ) {
            with(binding) {
                name.text = actor.name
                age.text = actor.age.toString()

                if (actor.isSelected) {
                    itemView.setBackgroundColor(Color.GRAY)
                } else {
                    itemView.setBackgroundColor(Color.TRANSPARENT)
                }
                setListeners(actor)
            }
        }

        private fun setListeners(
            actor: Actor
        ) {
            itemView.setOnClickListener {
                onClick(actor)
            }

            itemView.setOnLongClickListener {
                return@setOnLongClickListener onLongClick(actor)
            }
        }

        private fun onClick(actor: Actor) {
            if (selectedMode) {
                if (actor.isSelected) {
                    actor.isSelected = false
                    selectedItems.remove(actor)
                    itemView.setBackgroundColor(Color.TRANSPARENT)
                } else {
                    actor.isSelected = true
                    selectedItems.add(actor)
                    itemView.setBackgroundColor(Color.GRAY)
                }
                actions.itemSelected(actor)
            }
        }

        private fun onLongClick(actor: Actor): Boolean {
            if (!selectedMode) {
                notifyItemChanged(adapterPosition)
                actor.isSelected = true
                selectedItems.add(actor)
                itemView.setBackgroundColor(Color.GRAY)
                selectedMode = true
                actions.onStartSelectMode()
            }
            return true
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