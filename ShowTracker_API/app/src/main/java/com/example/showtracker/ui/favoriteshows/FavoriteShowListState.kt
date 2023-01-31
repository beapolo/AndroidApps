package com.example.showtracker.ui.favoriteshows

import com.example.showtracker.domain.model.Show

data class FavoriteShowListState(
    val showList: List<Show>? = ArrayList(),
    val message: String? = null,
    var isLoading: Boolean = false,
)