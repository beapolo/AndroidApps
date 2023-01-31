package com.example.showtracker.data.remote.api

import com.example.showtracker.data.common.Constants
import com.example.showtracker.data.model.responses.CreditsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ActorsAPI {

    @GET(Constants.CREDITS_PATH)
    suspend fun getSeasonCredits(
        @Path(Constants.PARAM_TV_ID) tvId: Int,
        @Path(Constants.PARAM_SEASON_NUMBER) seasonNumber: Int
    ): Response<CreditsResponse>
}