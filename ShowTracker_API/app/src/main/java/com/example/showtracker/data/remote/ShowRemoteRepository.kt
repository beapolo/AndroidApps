package com.example.showtracker.data.remote

import com.example.showtracker.data.model.responses.toShow
import com.example.showtracker.data.remote.api.TVShowsAPI
import com.example.showtracker.domain.model.Show
import com.example.showtracker.utils.NetworkResult
import javax.inject.Inject

class ShowRemoteRepository @Inject constructor(private val api: TVShowsAPI) :
    BaseApiResponse() {
    suspend fun getPopularShowList(): NetworkResult<List<Show?>?> {
        return safeApiCall(apiCall = { api.getPopularShowList() },
            transform = { showList ->
                showList.results?.map { it?.toShow() }
            })
    }

    suspend fun getShowDetails(id: Long): NetworkResult<Show?> {
        return safeApiCall(apiCall = { api.getShowDetails(id.toInt()) },
            transform = { it.toShow()
            })
    }
}