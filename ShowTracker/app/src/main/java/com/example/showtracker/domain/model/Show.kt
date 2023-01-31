package com.example.showtracker.domain.model

data class Show(
    var id: Long = 0,
    var title: String? = null,
    var genre: Enum<Genre>? = null,
    var streamingService: Enum<StreamingService>? = null,
    var isFavorite: Boolean = false,
    var episodeList: List<Episode> = ArrayList(),
    var actorList: List<Actor> = ArrayList(),

    var isSelected: Boolean = false,
)
