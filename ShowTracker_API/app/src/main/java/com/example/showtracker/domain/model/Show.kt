package com.example.showtracker.domain.model

import android.net.Uri

data class Show(
    var id: Long = 0,
    var title: String? = null,
    var genre: Enum<Genre>? = null,
    var mainGenre: String? = null,
    var streamingService: Enum<StreamingService>? = null,
    var network: Uri? = null,
    var isFavorite: Boolean = false,
    var seasonList: List<Season?> = ArrayList(),
    var episodeList: List<Episode> = ArrayList(),
    var actorList: List<Actor> = ArrayList(),
    var overview: String? = null,
    var rating: Double? = null,
    var img: Uri? = null,

    var isSelected: Boolean = false,
)
