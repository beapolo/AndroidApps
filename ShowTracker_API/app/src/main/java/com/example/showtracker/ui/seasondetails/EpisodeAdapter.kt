package com.example.showtracker.ui.seasondetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.showtracker.R
import com.example.showtracker.databinding.ItemEpisodeBinding
import com.example.showtracker.domain.model.Episode
import com.example.showtracker.ui.common.Constants

class EpisodeAdapter: ListAdapter<Episode, EpisodeAdapter.EpisodeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EpisodeViewHolder(layoutInflater.inflate(R.layout.item_episode, parent, false))
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }

    inner class EpisodeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemEpisodeBinding.bind(view)

        fun bind(
            episode: Episode
        ) {
            with(binding) {
                val epNumber = episode.season.toString() + Constants.X + episode.number.toString()
                number.text = epNumber
                episodeTitle.text = episode.title
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem == newItem
        }
    }
}