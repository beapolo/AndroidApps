package com.example.showtracker.ui.main

import androidx.lifecycle.*
import com.example.showtracker.R
import com.example.showtracker.domain.model.Show
import com.example.showtracker.domain.usecases.shows.DeleteShowListUC
import com.example.showtracker.domain.usecases.shows.GetShowListUC
import com.example.showtracker.domain.usecases.shows.InsertShowListWithEpisodesUC
import com.example.showtracker.domain.usecases.shows.UpdateShowListUC
import com.example.showtracker.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val getShowList: GetShowListUC,
    private val insertShowList: InsertShowListWithEpisodesUC,
    private val updateShowList: UpdateShowListUC,
    private val deleteShowList: DeleteShowListUC,
) : ViewModel() {

    private val _uiState = MutableLiveData(MainState())
    val uiState: LiveData<MainState> get() = _uiState

    fun handleEvent(event: MainEvent) {
        when (event) {
            MainEvent.GetAll -> getAll()
            MainEvent.MessageShown -> _uiState.value = _uiState.value?.copy(message = null)
            is MainEvent.InsertShowList -> insertShowList(event.list)
            is MainEvent.UpdateShowList -> updateShowList(event.list)
            is MainEvent.DeleteShowList -> deleteShowList(event.list)
        }
    }

    private fun dataBaseError() {
        _uiState.value = _uiState.value?.copy(
            message = stringProvider.getString(R.string.error),
        )
    }

    private fun getAll() {
        viewModelScope.launch {
            try {
                val showList = getShowList.invoke()
                _uiState.value = _uiState.value?.copy(
                    showList = showList,
                )

            } catch (e: Exception) {
                Timber.e(e.message, e)
                dataBaseError()
            }
        }
    }

    private fun insertShowList(shows: ArrayList<Show>) {
        viewModelScope.launch {
            try {
                insertShowList.invoke(shows)
                _uiState.value = _uiState.value?.copy(
                    showList = shows,
                )

            } catch (e: Exception) {
                Timber.e(e.message, e)
            }
        }
    }

    private fun updateShowList(shows: List<Show>) {
        viewModelScope.launch {
            try {
                updateShowList.invoke(shows)
                val finalList = getShowList.invoke()
                _uiState.value = _uiState.value?.copy(
                    showList = finalList,
                    message = stringProvider.getString(R.string.updated)
                )
            } catch (e: Exception) {
                Timber.e(e.message, e)
                dataBaseError()
            }
        }
    }

    private fun deleteShowList(shows: List<Show>) {
        viewModelScope.launch {
            try {
                deleteShowList.invoke(shows)
                val finalList = getShowList.invoke()
                _uiState.value = _uiState.value?.copy(
                    showList = finalList,
                    message = stringProvider.getString(R.string.deleted)
                )
            } catch (e: Exception) {
                Timber.e(e.message, e)
                dataBaseError()
            }
        }
    }

}