package com.example.showtracker.ui.showdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.showtracker.R
import com.example.showtracker.databinding.ItemSeasonBinding
import com.example.showtracker.domain.model.Season
import com.example.showtracker.ui.common.Constants

class SeasonAdapter(
    private val actions: SeasonActions,
) : ListAdapter<Season, SeasonAdapter.SeasonViewHolder>(DiffCallback()) {
    interface SeasonActions {
        fun onClickShowSeason(seasonNumber: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SeasonViewHolder(layoutInflater.inflate(R.layout.item_season, parent, false))
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }

    inner class SeasonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemSeasonBinding.bind(view)

        fun bind(season: Season) {
            with(binding) {
                val seasonNum = Constants.SPACE + season.number.toString()
                seasonNumber.text = seasonNum
                val numberOfEpisodes = season.episodeCount
                if (numberOfEpisodes == null) {
                    episodeCount.visibility = View.GONE
                } else {
                    val numOfEpisodes = Constants.SPACE + numberOfEpisodes.toString()
                    episodeCount.text = numOfEpisodes
                }
            }
            setListeners(season)
        }

        private fun setListeners(season: Season) {
            itemView.setOnClickListener {
                season.number?.let { number -> actions.onClickShowSeason(number) }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Season>() {
        override fun areItemsTheSame(oldItem: Season, newItem: Season): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Season, newItem: Season): Boolean {
            return oldItem == newItem
        }
    }
}