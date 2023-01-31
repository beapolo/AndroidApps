package com.example.showtracker.data.remote.api

import com.example.showtracker.data.common.Constants
import com.example.showtracker.data.model.responses.ShowListResponse
import com.example.showtracker.data.model.responses.ShowResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TVShowsAPI {

    @GET(Constants.POPULAR_SHOWS_PATH)
    suspend fun getPopularShowList(): Response<ShowListResponse>

    @GET(Constants.SINGLE_SHOW_PATH)
    suspend fun getShowDetails(@Path(Constants.PARAM_TV_ID) id: Int): Response<ShowResponse>
}