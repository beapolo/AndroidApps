package com.example.showtracker.data.model.responses

import com.example.showtracker.data.common.Constants
import com.google.gson.annotations.SerializedName

data class ShowResponse(
    @SerializedName(Constants.COLUMN_ORIGINAL_LANGUAGE)
    val originalLanguage: String? = null,
    @SerializedName(Constants.COLUMN_NUMBER_OF_EPISODES)
    val numberOfEpisodes: Int? = null,
    val networks: List<NetworksItem?>? = null,
    val type: String? = null,
    @SerializedName(Constants.COLUMN_BACKDROP_PATH)
    val backdropPath: String? = null,
    val genres: List<GenresItem?>? = null,
    val popularity: Any? = null,
    @SerializedName(Constants.COLUMN_PRODUCTION_COUNTRIES)
    val productionCountries: List<ProductionCountriesItem?>? = null,
    val id: Int? = null,
    @SerializedName(Constants.COLUMN_NUMBER_OF_SEASONS)
    val numberOfSeasons: Int? = null,
    @SerializedName(Constants.COLUMN_VOTE_COUNT)
    val voteCount: Int? = null,
    @SerializedName(Constants.COLUMN_FIRST_AIR_DATE)
    val firstAirDate: String? = null,
    val overview: String? = null,
    val seasons: List<SeasonsItem?>? = null,
    val languages: List<String?>? = null,
    @SerializedName(Constants.COLUMN_CREATED_BY)
    val createdBy: List<CreatedByItem?>? = null,
    @SerializedName(Constants.COLUMN_LAST_EPISODE)
    val lastEpisodeToAir: LastEpisodeToAir? = null,
    @SerializedName(Constants.COLUMN_POSTER_PATH)
    val posterPath: String? = null,
    @SerializedName(Constants.COLUMN_ORIGIN_COUNTRY)
    val originCountry: List<String?>? = null,
    @SerializedName(Constants.COLUMN_SPOKEN_LANGUAGES)
    val spokenLanguages: List<SpokenLanguagesItem?>? = null,
    @SerializedName(Constants.COLUMN_PRODUCTION_COMPANIES)
    val productionCompanies: List<ProductionCompaniesItem?>? = null,
    @SerializedName(Constants.COLUMN_ORIGINAL_NAME)
    val originalName: String? = null,
    @SerializedName(Constants.COLUMN_VOTE_AVERAGE)
    val voteAverage: Any? = null,
    val name: String? = null,
    val tagline: String? = null,
    @SerializedName(Constants.COLUMN_EPISODE_TIME)
    val episodeRunTime: List<Any?>? = null,
    val adult: Boolean? = null,
    @SerializedName(Constants.COLUMN_NEXT_EPISODE)
    val nextEpisodeToAir: Any? = null,
    @SerializedName(Constants.COLUMN_IN_PRODUCTION)
    val inProduction: Boolean? = null,
    @SerializedName(Constants.COLUMN_LAST_AIR_DATE)
    val lastAirDate: String? = null,
    val homepage: String? = null,
    val status: String? = null
)

data class CreatedByItem(
    val gender: Int? = null,
    @SerializedName(Constants.COLUMN_CREDIT_ID)
    val creditId: String? = null,
    val name: String? = null,
    @SerializedName(Constants.COLUMN_PROFILE_PATH)
    val profilePath: String? = null,
    val id: Int? = null
)

data class LastEpisodeToAir(
    @SerializedName(Constants.COLUMN_PRODUCTION_CODE)
    val productionCode: String? = null,
    @SerializedName(Constants.COLUMN_AIR_DATE)
    val airDate: String? = null,
    val overview: String? = null,
    @SerializedName(Constants.COLUMN_EPISODE_NUMBER)
    val episodeNumber: Int? = null,
    @SerializedName(Constants.COLUMN_SHOW_ID)
    val showId: Int? = null,
    @SerializedName(Constants.COLUMN_VOTE_AVERAGE)
    val voteAverage: Any? = null,
    val name: String? = null,
    @SerializedName(Constants.COLUMN_SEASON_NUMBER)
    val seasonNumber: Int? = null,
    val runtime: Int? = null,
    val id: Int? = null,
    @SerializedName(Constants.COLUMN_STILL_PATH)
    val stillPath: String? = null,
    @SerializedName(Constants.COLUMN_VOTE_COUNT)
    val voteCount: Int? = null
)

data class ProductionCompaniesItem(
    @SerializedName(Constants.COLUMN_LOGO_PATH)
    val logoPath: String? = null,
    val name: String? = null,
    val id: Int? = null,
    @SerializedName(Constants.COLUMN_ORIGIN_COUNTRY)
    val originCountry: String? = null
)

data class SeasonsItem(
    @SerializedName(Constants.COLUMN_AIR_DATE)
    val airDate: String? = null,
    val overview: String? = null,
    @SerializedName(Constants.COLUMN_EPISODE_COUNT)
    val episodeCount: Int? = null,
    val name: String? = null,
    @SerializedName(Constants.COLUMN_SEASON_NUMBER)
    val seasonNumber: Int? = null,
    val id: Int? = null,
    @SerializedName(Constants.COLUMN_POSTER_PATH)
    val posterPath: String? = null
)

data class GenresItem(
    val name: String? = null,
    val id: Int? = null
)

data class NetworksItem(
    @SerializedName(Constants.COLUMN_LOGO_PATH)
    val logoPath: String? = null,
    val name: String? = null,
    val id: Int? = null,
    @SerializedName(Constants.COLUMN_ORIGIN_COUNTRY)
    val originCountry: String? = null
)

data class ProductionCountriesItem(
    @SerializedName(Constants.COLUMN_ISO_3166_1)
    val iso31661: String? = null,
    val name: String? = null
)

data class SpokenLanguagesItem(
    val name: String? = null,
    @SerializedName(Constants.COLUMN_ISO_639_1)
    val iso6391: String? = null,
    @SerializedName(Constants.COLUMN_ENGLISH)
    val englishName: String? = null
)

