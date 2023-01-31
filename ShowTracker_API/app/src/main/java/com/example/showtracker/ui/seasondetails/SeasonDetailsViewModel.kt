package com.example.showtracker.ui.seasondetails

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showtracker.domain.usecases.actors.GetActorsBySeasonUC
import com.example.showtracker.domain.usecases.episodes.*
import com.example.showtracker.ui.common.Constants
import com.example.showtracker.utils.NetworkResult
import com.example.showtracker.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeasonDetailsViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    private val getEpisodes: GetEpisodesBySeasonUC,
    private val getActors: GetActorsBySeasonUC,
) : ViewModel() {

    private val _uiState: MutableStateFlow<SeasonDetailsState> by lazy {
        MutableStateFlow(SeasonDetailsState())
    }
    val uiState: StateFlow<SeasonDetailsState> = _uiState

    fun handleEvent(event: SeasonDetailsEvent) {
        when (event) {
            is SeasonDetailsEvent.GetEpisodes -> getEpisodes(event.tvId, event.seasonNumber)
            is SeasonDetailsEvent.GetActors -> getActors(event.tvId, event.seasonNumber)
            SeasonDetailsEvent.MessageShown -> _uiState.update { it.copy(message = null) }
        }
    }

    private fun internetConnectionError() {
        _uiState.update {
            it.copy(
                message = Constants.CONNECTION_FAILED,
                isLoading = false
            )
        }
    }

    private fun getEpisodes(tvId: Long, seasonNumber: Int) {
        viewModelScope.launch {
            if (Utils.hasInternetConnection(context = appContext)) {
                getEpisodes.invoke(tvId, seasonNumber)
                    .collect { result ->
                        when (result) {
                            is NetworkResult.Success -> _uiState.update {
                                it.copy(
                                    episodes = result.data,
                                    isLoading = false
                                )
                            }
                            is NetworkResult.Error -> {
                                _uiState.update {
                                    it.copy(
                                        message = Constants.EPISODES_NOT_FOUND,
                                        isLoading = false
                                    )
                                }
                            }
                            is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                        }
                    }
            } else {
                internetConnectionError()
            }
        }
    }

    private fun getActors(tvId: Long, seasonNumber: Int) {
        viewModelScope.launch {
            if (Utils.hasInternetConnection(context = appContext)) {
                getActors.invoke(tvId, seasonNumber)
                    .collect { result ->
                        when (result) {
                            is NetworkResult.Success -> _uiState.update {
                                it.copy(
                                    actors = result.data,
                                    isLoading = false
                                )
                            }
                            is NetworkResult.Error -> {
                                _uiState.update {
                                    it.copy(
                                        message = Constants.ACTORS_NOT_FOUND,
                                        isLoading = false
                                    )
                                }
                            }
                            is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                        }
                    }
            } else {
                internetConnectionError()
            }
        }
    }
}