package com.example.showtracker.data.model.responses

import com.example.showtracker.data.common.Constants
import com.google.gson.annotations.SerializedName

data class CreditsResponse(
	val cast: List<CastItem?>? = null,
	val id: Int? = null,
	val crew: List<CrewItem?>? = null
)

data class CrewItem(
	val gender: Int? = null,
	@SerializedName(Constants.COLUMN_CREDIT_ID)
	val creditId: String? = null,
	@SerializedName(Constants.COLUMN_DEPARTMENT)
	val knownForDepartment: String? = null,
	@SerializedName(Constants.COLUMN_ORIGINAL_NAME)
	val originalName: String? = null,
	val popularity: Any? = null,
	val name: String? = null,
	@SerializedName(Constants.COLUMN_PROFILE_PATH)
	val profilePath: String? = null,
	val id: Int? = null,
	val adult: Boolean? = null,
	val department: String? = null,
	val job: String? = null
)

data class CastItem(
	val character: String? = null,
	val gender: Int? = null,
	@SerializedName(Constants.COLUMN_CREDIT_ID)
	val creditId: String? = null,
	@SerializedName(Constants.COLUMN_DEPARTMENT)
	val knownForDepartment: String? = null,
	@SerializedName(Constants.COLUMN_ORIGINAL_NAME)
	val originalName: String? = null,
	val popularity: Any? = null,
	val name: String? = null,
	@SerializedName(Constants.COLUMN_PROFILE_PATH)
	val profilePath: String? = null,
	val id: Int? = null,
	val adult: Boolean? = null,
	val order: Int? = null
)

