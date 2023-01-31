package com.example.showtracker.data.model

import com.example.showtracker.data.model.showactor.ActorEntity
import com.example.showtracker.data.model.showactor.ActorWithShows
import com.example.showtracker.data.model.showepisode.EpisodeEntity
import com.example.showtracker.data.model.showepisode.ShowWithEpisodes
import com.example.showtracker.domain.model.*

fun ShowEntity.toShow(): Show {
    val genre: Genre? = this.genre?.let { Genre.valueOf(it) }
    val streamingService: StreamingService? =
        this.streamingService?.let { StreamingService.valueOf(it) }
    val isFavorite = this.isFavorite == 1
    return Show(this.showId, this.showTitle, genre, streamingService, isFavorite)
}

fun EpisodeEntity.toEpisode() = Episode(
    this.episodeId,
    this.episodeTitle,
    this.number,
    this.season,
    Show(this.showId),
    this.isWatched == 1
)

fun Actor.toActorEntity() = ActorEntity(this.id, this.name, this.age)

fun Show.toShowEntity() = ShowEntity(
    this.id,
    this.title,
    this.genre?.name,
    this.streamingService?.name,
    if (isFavorite) 1 else 0
)

fun Episode.toEpisodeEntity() = EpisodeEntity(
    this.id,
    this.title,
    this.number,
    this.season,
    this.show.id,
    if (isWatched) 1 else 0,
)

fun ShowWithEpisodes.toShow(): Show {
    val genre: Genre? = this.show.genre?.let { Genre.valueOf(it) }
    val streamingService: StreamingService? =
        this.show.streamingService?.let { StreamingService.valueOf(it) }
    val isFavorite = this.show.isFavorite == 1
    return Show(
        this.show.showId,
        this.show.showTitle,
        genre,
        streamingService,
        isFavorite,
        this.episodes.map { it.toEpisode() })
}

fun ActorWithShows.toActor() = Actor(
    this.actor.actorId,
    this.actor.name,
    this.actor.age,
    this.shows.map { it.toShow() }
)

