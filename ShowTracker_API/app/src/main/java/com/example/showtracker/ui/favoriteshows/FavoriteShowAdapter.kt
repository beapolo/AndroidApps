package com.example.showtracker.ui.favoriteshows

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.showtracker.R
import com.example.showtracker.databinding.ItemFavoriteShowBinding
import com.example.showtracker.domain.model.Show

class FavoriteShowAdapter(
    val context: Context?,
    private val actions: ShowActions,
) : ListAdapter<Show, FavoriteShowAdapter.ShowViewHolder>(DiffCallback()) {

    interface ShowActions {
        fun onClickShow(id: Long)
        fun onStartSelectMode()
        fun itemSelected(show: Show)
    }

    private var selectedItems = mutableListOf<Show>()
    fun getSelectedItems() = selectedItems.toList()

    fun resetSelectMode() {
        selectedItems.forEach { it.isSelected = false }
        selectedItems.removeAll(getSelectedItems())
        selectedMode = false
        notifyDataSetChanged()
    }

    private var selectedMode: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ShowViewHolder(layoutInflater.inflate(R.layout.item_favorite_show, parent, false))
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }

    inner class ShowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemFavoriteShowBinding.bind(view)

        fun bind(show: Show) {
            with(binding) {
                showImg.load(show.img)
                title.text = show.title

                if (show.isSelected) {
                    itemView.setBackgroundColor(Color.GRAY)
                } else {
                    itemView.setBackgroundColor(Color.TRANSPARENT)
                }
                setListeners(show)
            }
        }

        private fun setListeners(show: Show) {
            itemView.setOnClickListener {
                onClick(show)
            }

            itemView.setOnLongClickListener {
                return@setOnLongClickListener onLongClick(show)
            }
        }

        private fun onClick(show: Show) {
            if (selectedMode) {
                if (show.isSelected) {
                    show.isSelected = false
                    selectedItems.remove(show)
                    itemView.setBackgroundColor(Color.TRANSPARENT)
                } else {
                    show.isSelected = true
                    selectedItems.add(show)
                    itemView.setBackgroundColor(Color.GRAY)
                }
                actions.itemSelected(show)
            } else {
                actions.onClickShow(show.id)
            }
        }

        private fun onLongClick(show: Show): Boolean {
            if (!selectedMode) {
                notifyItemChanged(adapterPosition)
                show.isSelected = true
                selectedItems.add(show)
                itemView.setBackgroundColor(Color.GRAY)
                selectedMode = true
                actions.onStartSelectMode()
            }
            return true
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Show>() {
        override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean {
            return oldItem == newItem
        }
    }
}