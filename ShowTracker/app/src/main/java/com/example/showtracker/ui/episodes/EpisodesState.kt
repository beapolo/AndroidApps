package com.example.showtracker.ui.episodes

import com.example.showtracker.domain.model.Show

data class EpisodesState(
    val show: Show = Show(),
    val message: String? = null,
    val dialogMessage: String? = null,
    val messageWithUndo: String? = null,
)