package com.example.showtracker.ui.episodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showtracker.R
import com.example.showtracker.domain.model.Episode
import com.example.showtracker.domain.usecases.episodes.*
import com.example.showtracker.domain.usecases.shows.GetShowWithEpisodesUC
import com.example.showtracker.ui.common.Constants
import com.example.showtracker.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val getShow: GetShowWithEpisodesUC,
    private val insertEpisode: InsertEpisodeUC,
    private val updateEpisode: UpdateEpisodeUC,
    private val deleteEpisode: DeleteEpisodeUC,
) : ViewModel() {

    private val _uiState = MutableLiveData(EpisodesState())
    val uiState: LiveData<EpisodesState> get() = _uiState

    fun handleEvent(event: EpisodesEvent) {
        when (event) {
            is EpisodesEvent.GetShow -> getShow(event.id)
            is EpisodesEvent.InsertEpisode -> insertEpisode(event.episode)
            is EpisodesEvent.UpdateEpisode -> updateEpisode(event.episode)
            is EpisodesEvent.DeleteEpisode -> deleteEpisode(event.episode)
            EpisodesEvent.MessageShown -> _uiState.value =
                _uiState.value?.copy(message = null, dialogMessage = null, messageWithUndo = null)
        }
    }

    private fun dataBaseError() {
        _uiState.value = _uiState.value?.copy(
            message = stringProvider.getString(R.string.error),
        )
    }

    private fun getShow(id: Long) {
        viewModelScope.launch {
            try {
                val show = getShow.invoke(id)
                _uiState.value = _uiState.value?.copy(
                    show = show,
                )

            } catch (e: Exception) {
                Timber.e(e.message, e.localizedMessage)
                dataBaseError()
            }
        }
    }

    private fun insertEpisode(episode: Episode) {
        viewModelScope.launch {
            try {
                val result = insertEpisode.invoke(episode)
                if (result > 0) {
                    val show = getShow.invoke(episode.show.id)
                    _uiState.value = _uiState.value?.copy(
                        show = show,
                        messageWithUndo = stringProvider.getString(R.string.added)
                    )
                } else if (result == com.example.showtracker.domain.model.Error.FILL_OPTIONS.error) {
                    _uiState.value = _uiState.value?.copy(
                        dialogMessage = stringProvider.getString(R.string.fill_fields)
                    )
                } else if (result == com.example.showtracker.domain.model.Error.REPEATED_NAME.error) {
                    _uiState.value = _uiState.value?.copy(
                        dialogMessage = stringProvider.getString(R.string.repeated_title)
                    )
                } else if (result == com.example.showtracker.domain.model.Error.WRONG_EPISODE_NUMBER.error) {
                    _uiState.value = _uiState.value?.copy(
                        dialogMessage = stringProvider.getString(R.string.episode) + Constants.SPACE
                                + episode.season + Constants.X
                                + episode.number + Constants.SPACE
                                + stringProvider.getString(R.string.exist)
                    )
                }
            } catch (e: Exception) {
                Timber.e(e.message, e)
                dataBaseError()
            }
        }
    }

    private fun updateEpisode(episode: Episode) {
        viewModelScope.launch {
            try {
                updateEpisode.invoke(episode)
                val show = getShow.invoke(episode.show.id)
                _uiState.value = _uiState.value?.copy(
                    show = show,
                )
            } catch (e: Exception) {
                Timber.e(e.message, e)
                dataBaseError()
            }
        }
    }

    private fun deleteEpisode(episode: Episode) {
        viewModelScope.launch {
            try {
                deleteEpisode.invoke(episode)
                val show = getShow.invoke(episode.show.id)
                _uiState.value = _uiState.value?.copy(
                    show = show,
                    message = stringProvider.getString(R.string.undone)
                )
            } catch (e: Exception) {
                Timber.e(e.message, e)
                dataBaseError()
            }
        }
    }
}