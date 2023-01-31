package com.example.showtracker.data.model.entities

import android.net.Uri
import com.example.showtracker.domain.model.*

fun ShowEntity.toShow(): Show {
    val genre: Genre? = this.genre?.let { Genre.valueOf(it) }
    val streamingService: StreamingService? =
        this.streamingService?.let { StreamingService.valueOf(it) }
    val isFavorite = this.isFavorite == 1
    val img = if (this.img == null) null else Uri.parse(this.img)
    return Show(
        id = this.showId,
        title = this.showTitle,
        genre = genre,
        streamingService = streamingService,
        isFavorite = isFavorite,
        img = img,
        overview = this.overview
    )
}

fun Show.toShowEntity() = ShowEntity(
    this.id,
    this.title,
    this.genre?.name,
    this.streamingService?.name,
    if (isFavorite) 1 else 0,
    if (this.img == null) null else this.img.toString(),
    this.overview
)

fun Season.toSeasonEntity() = SeasonEntity(
    this.id,
    this.number,
    this.overview,
    this.episodeCount,
    this.show.id
)

fun SeasonEntity.toSeason() = Season(
    this.id,
    this.number,
    this.overview,
    this.episodeCount,
    Show(this.showId)
)

fun ShowWithSeasons.toShow(): Show{
    val genre: Genre? = this.show.genre?.let { Genre.valueOf(it) }
    val streamingService: StreamingService? =
        this.show.streamingService?.let { StreamingService.valueOf(it) }
    val isFavorite = this.show.isFavorite == 1
    val img = if (this.show.img == null) null else Uri.parse(this.show.img)
    return Show(
        id = this.show.showId,
        title = this.show.showTitle,
        genre = genre,
        streamingService = streamingService,
        isFavorite = isFavorite,
        img = img,
        overview = this.show.overview,
        seasonList = this.seasons.map { it.toSeason() }
    )
}

