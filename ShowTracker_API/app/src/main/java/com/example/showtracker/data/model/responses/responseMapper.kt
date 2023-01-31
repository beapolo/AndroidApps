package com.example.showtracker.data.model.responses

import android.net.Uri
import com.example.showtracker.data.common.Constants
import com.example.showtracker.domain.model.Actor
import com.example.showtracker.domain.model.Episode
import com.example.showtracker.domain.model.Season
import com.example.showtracker.domain.model.Show

fun ResultsItem.toShow() = this.id?.let { it ->
    val posterImg =
        if (this.posterPath != null)
            Uri.parse(Constants.IMG_BASE_URL + this.posterPath)
        else null
    Show(
        id = it.toLong(),
        title = this.name,
        rating = this.voteAverage.takeIf { rating -> rating is Double } as Double?,
        overview = this.overview,
        img = posterImg
    )
}

fun ShowResponse.toShow() = this.id?.let {
    val networkLogo =
        if (!networks.isNullOrEmpty())
            Uri.parse(Constants.IMG_BASE_URL + this.networks[0]?.logoPath)
        else null

    val genre =
        if (!genres.isNullOrEmpty())
            genres[0]?.name
        else null

    val img =
        if (this.posterPath != null)
            Uri.parse(Constants.IMG_BASE_URL + this.posterPath)
        else null

    val seasonList =
        if (!seasons.isNullOrEmpty())
            seasons.map { seasonsItem -> seasonsItem?.toSeason() }
        else ArrayList()

    Show(
        id = it.toLong(),
        title = this.name,
        overview = this.overview,
        mainGenre = genre,
        network = networkLogo,
        img = img,
        seasonList = seasonList,
    )
}

fun SeasonsItem.toSeason() = this.id?.let {
    Season(
        id = it.toLong(),
        number = this.seasonNumber,
        overview = this.overview,
        episodeCount = this.episodeCount
    )
}

fun EpisodesItem.toEpisode() = this.id?.let {
    Episode(
        id = it.toLong(),
        title = this.name,
        number = this.episodeNumber,
        season = this.seasonNumber,
    )
}

fun CastItem.toActor() = this.id?.let {
    val img =
        if (this.profilePath != null)
            Uri.parse(Constants.IMG_BASE_URL + this.profilePath)
        else null
    Actor(
        id = it.toLong(),
        name = originalName,
        character = character,
        img = img,
    )
}