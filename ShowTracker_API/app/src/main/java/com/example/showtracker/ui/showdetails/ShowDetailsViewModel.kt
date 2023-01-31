package com.example.showtracker.ui.showdetails

import androidx.lifecycle.*
import com.example.showtracker.domain.usecases.shows.GetShowDetailsUC
import com.example.showtracker.ui.common.Constants
import com.example.showtracker.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailsViewModel @Inject constructor(
    private val getShow: GetShowDetailsUC,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ShowDetailsState> by lazy {
        MutableStateFlow(ShowDetailsState())
    }
    val uiState: StateFlow<ShowDetailsState> = _uiState

    fun handleEvent(event: ShowDetailsEvent) {
        when (event) {
            is ShowDetailsEvent.GetShow -> getShow(event.id)
            ShowDetailsEvent.MessageShown -> _uiState.update { it.copy(message = null) }
        }
    }

    private fun getShow(id: Long) {
        viewModelScope.launch {
            getShow.invoke(id)
                .collect { result ->
                    when (result) {
                        is NetworkResult.Success -> _uiState.update {
                            it.copy(
                                show = result.data,
                                isLoading = false
                            )
                        }
                        is NetworkResult.Error -> {
                            if (result.data == null) {
                                _uiState.update {
                                    it.copy(
                                        message = Constants.CONNECTION_FAILED,
                                    )
                                }
                            }
                            _uiState.update {
                                it.copy(
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