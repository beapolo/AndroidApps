package com.example.showtracker.ui.main

import android.content.Context
import androidx.lifecycle.*
import com.example.showtracker.domain.model.Show
import com.example.showtracker.domain.usecases.shows.GetPopularShowListUC
import com.example.showtracker.domain.usecases.shows.SaveShowListAsFavoriteUC
import com.example.showtracker.ui.common.Constants
import com.example.showtracker.utils.NetworkResult
import com.example.showtracker.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    private val getPopularShowList: GetPopularShowListUC,
    private val saveFavouriteShowList: SaveShowListAsFavoriteUC,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainState> by lazy {
        MutableStateFlow(MainState())
    }
    val uiState: StateFlow<MainState> = _uiState

    init {
        getPopularShows()
    }

    fun handleEvent(event: MainEvent) {
        when (event) {
            MainEvent.GetAll -> getPopularShows()
            MainEvent.MessageShown -> _uiState.update { it.copy(message = null) }
            is MainEvent.SaveShowListAsFavorites -> saveShowListAsFavorites(event.list)
        }
    }

    private fun getPopularShows() {
        viewModelScope.launch {
            if (Utils.hasInternetConnection(context = appContext)) {
                getPopularShowList.invoke()
                    .collect { result ->
                        when (result) {
                            is NetworkResult.Success -> _uiState.update {
                                it.copy(
                                    showList = result.data,
                                    isLoading = false
                                )
                            }
                            is NetworkResult.Error -> {
                                _uiState.update {
                                    it.copy(
                                        message = Constants.SHOWS_NOT_FOUND,
                                        isLoading = false
                                    )
                                }
                            }
                            is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                        }
                    }
            } else {
                _uiState.update {
                    it.copy(
                        message = Constants.CONNECTION_FAILED,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun saveShowListAsFavorites(shows: List<Show>) {
        viewModelScope.launch {
            saveFavouriteShowList.invoke(shows)
                .collect { result ->
                    when (result) {
                        is NetworkResult.Success -> _uiState.update {
                            it.copy(
                                message = Constants.ADDED_TO_FAVORITES,
                                isLoading = false
                            )
                        }
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    message = it.message,
                                    isLoading = false
                                )
                            }
                        }
                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                    }
                }
        }
    }
}