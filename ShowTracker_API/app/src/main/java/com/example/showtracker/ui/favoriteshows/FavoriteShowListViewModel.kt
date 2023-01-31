package com.example.showtracker.ui.favoriteshows

import androidx.lifecycle.*
import com.example.showtracker.domain.model.Show
import com.example.showtracker.domain.usecases.shows.GetFavoriteShowListUC
import com.example.showtracker.domain.usecases.shows.RemoveShowListFromFavoritesUC
import com.example.showtracker.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteShowListViewModel @Inject constructor(
    private val getShowList: GetFavoriteShowListUC,
    private val removeShowListFromFavorites: RemoveShowListFromFavoritesUC,
) : ViewModel() {

    private val _uiState: MutableStateFlow<FavoriteShowListState> by lazy {
        MutableStateFlow(FavoriteShowListState())
    }
    val uiState: StateFlow<FavoriteShowListState> = _uiState

    fun handleEvent(event: FavoriteShowListEvent) {
        when (event) {
            is FavoriteShowListEvent.GetFavoriteShowList -> getFavoriteShowList()
            is FavoriteShowListEvent.RemoveShowListFromFavorites -> removeShowListFromFavorites(
                event.list
            )
            FavoriteShowListEvent.MessageShown -> _uiState.update { it.copy(message = null) }
        }
    }

    private fun getFavoriteShowList() {
        viewModelScope.launch {
            getShowList.invoke()
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
                                    message = result.message,
                                    isLoading = false
                                )
                            }
                        }
                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                    }
                }
        }
    }

    private fun removeShowListFromFavorites(shows: List<Show>) {
        viewModelScope.launch {
            removeShowListFromFavorites.invoke(shows)
                .collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            getFavoriteShowList()
                            _uiState.update {
                                it.copy(isLoading = false)
                            }
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