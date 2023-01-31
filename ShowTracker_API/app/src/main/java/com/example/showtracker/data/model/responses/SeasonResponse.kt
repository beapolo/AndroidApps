package com.example.showtracker.data.model.responses

import com.example.showtracker.data.common.Constants
import com.google.gson.annotations.SerializedName

data class SeasonResponse(
	@SerializedName(Constants.COLUMN_AIR_DATE)
	val airDate: String? = null,
	val overview: String? = null,
	val name: String? = null,
	@SerializedName(Constants.COLUMN_SEASON_NUMBER)
	val seasonNumber: Int? = null,
	@SerializedName(Constants.COLUMN_STRING_ID)
	val strId: String? = null,
	val id: Int? = null,
	val episodes: List<EpisodesItem?>? = null,
	@SerializedName(Constants.COLUMN_POSTER_PATH)
	val posterPath: String? = null
)

data class EpisodesItem(
	@SerializedName(Constants.COLUMN_PRODUCTION_CODE)
	val productionCode: String? = null,
	val overview: String? = null,
	@SerializedName(Constants.COLUMN_SHOW_ID)
	val showId: Int? = null,
	@SerializedName(Constants.COLUMN_SEASON_NUMBER)
	val seasonNumber: Int? = null,
	val runtime: Int? = null,
	@SerializedName(Constants.COLUMN_STILL_PATH)
	val stillPath: String? = null,
	val crew: List<Any?>? = null,
	@SerializedName(Constants.COLUMN_GUEST_STARS)
	val guestStars: List<Any?>? = null,
	@SerializedName(Constants.COLUMN_AIR_DATE)
	val airDate: String? = null,
	@SerializedName(Constants.COLUMN_EPISODE_NUMBER)
	val episodeNumber: Int? = null,
	@SerializedName(Constants.COLUMN_VOTE_AVERAGE)
	val voteAverage: Any? = null,
	val name: String? = null,
	val id: Int? = null,
	@SerializedName(Constants.COLUMN_VOTE_COUNT)
	val voteCount: Int? = null
)

