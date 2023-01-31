package com.example.showtracker.domain.model

data class Episode(
    var id: Long = 0,
    var title: String? = null,
    var number: Int? = null,
    var season: Int? = null,
    var show: Show = Show(),
    var isWatched: Boolean = false,
)
