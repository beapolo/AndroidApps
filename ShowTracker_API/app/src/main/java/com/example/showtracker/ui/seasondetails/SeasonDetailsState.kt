package com.example.showtracker.ui.seasondetails

import com.example.showtracker.domain.model.*

data class SeasonDetailsState(
    val episodes: List<Episode?>? = ArrayList(),
    val actors: List<Actor?>? = ArrayList(),
    val isLoading: Boolean = false,
    val message: String? = null,
)