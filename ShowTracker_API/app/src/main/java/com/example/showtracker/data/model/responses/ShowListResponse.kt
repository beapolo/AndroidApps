package com.example.showtracker.data.model.responses

import com.example.showtracker.data.common.Constants
import com.google.gson.annotations.SerializedName

data class ShowListResponse(
	val page: Int? = null,
	@SerializedName(Constants.COLUMN_TOTAL_PAGES)
	val totalPages: Int? = null,
	val results: List<ResultsItem?>? = null,
	@SerializedName(Constants.COLUMN_TOTAL_RESULTS)
	val totalResults: Int? = null
)

data class ResultsItem(
	@SerializedName(Constants.COLUMN_FIRST_AIR_DATE)
	val firstAirDate: String? = null,
	val overview: String? = null,
	@SerializedName(Constants.COLUMN_ORIGINAL_LANGUAGE)
	val originalLanguage: String? = null,
	@SerializedName(Constants.COLUMN_GENRE_IDS)
	val genreIds: List<Int?>? = null,
	@SerializedName(Constants.COLUMN_POSTER_PATH)
	val posterPath: String? = null,
	@SerializedName(Constants.COLUMN_ORIGIN_COUNTRY)
	val originCountry: List<String?>? = null,
	@SerializedName(Constants.COLUMN_BACKDROP_PATH)
	val backdropPath: String? = null,
	@SerializedName(Constants.COLUMN_ORIGINAL_NAME)
	val originalName: String? = null,
	val popularity: Any? = null,
	@SerializedName(Constants.COLUMN_VOTE_AVERAGE)
	val voteAverage: Any? = null,
	val name: String? = null,
	val id: Int? = null,
	@SerializedName(Constants.COLUMN_VOTE_COUNT)
	val voteCount: Int? = null
)

