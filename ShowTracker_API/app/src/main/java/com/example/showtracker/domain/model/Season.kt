package com.example.showtracker.domain.model

data class Season(
    var id: Long = 0,
    var number: Int? = null,
    var overview: String? = null,
    var episodeCount: Int? = null,
    var show: Show = Show(),
)
