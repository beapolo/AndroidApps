package com.example.showtracker.ui.actors

import androidx.lifecycle.*
import com.example.showtracker.R
import com.example.showtracker.domain.model.Actor
import com.example.showtracker.domain.usecases.actors.DeleteActorListUC
import com.example.showtracker.domain.usecases.actors.GetActorListUC
import com.example.showtracker.domain.usecases.actors.InsertActorListByShowIdUC
import com.example.showtracker.domain.usecases.shows.GetActorListByShowUC
import com.example.showtracker.ui.common.Constants
import com.example.showtracker.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ActorViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val getActorList: GetActorListUC,
    private val getActorListByShow: GetActorListByShowUC,
    private val insertActorByShowId: InsertActorListByShowIdUC,
    private val deleteActorList: DeleteActorListUC,
) : ViewModel() {

    private val _uiState = MutableLiveData(ActorState())
    val uiState: LiveData<ActorState> get() = _uiState

    fun handleEvent(event: ActorEvent) {
        when (event) {
            ActorEvent.GetAll -> getAll()
            is ActorEvent.GetByShow -> getByShow(event.showId)
            is ActorEvent.InsertActorList -> insertActor(event.actorList, event.showId)
            is ActorEvent.DeleteActorList -> deleteActorList(event.actorList, event.showId)
            ActorEvent.MessageShown -> _uiState.value =
                _uiState.value?.copy(message = null, messageWithUndo = null)
        }
    }

    private fun dataBaseError() {
        _uiState.value = _uiState.value?.copy(
            message = stringProvider.getString(R.string.error),
        )
    }

    private fun getAll(): List<Actor> {
        var actorList: List<Actor> = ArrayList()
        viewModelScope.launch {
            try {
                actorList = getActorList.invoke()
                _uiState.value = _uiState.value?.copy(
                    actorList = actorList,
                )

            } catch (e: Exception) {
                Timber.e(e.message, e)
                dataBaseError()
            }
        }
        return actorList
    }

    private fun getByShow(id: Long): List<Actor> {
        var actorList: List<Actor> = ArrayList()
        viewModelScope.launch {
            try {
                actorList = getActorListByShow.invoke(id)
                _uiState.value = _uiState.value?.copy(
                    actorList = actorList,
                )

            } catch (e: Exception) {
                Timber.e(e.message, e)
                dataBaseError()
            }
        }
        return actorList
    }

    private fun deleteActorList(list: List<Actor>, showId: Long) {
        viewModelScope.launch {
            try {
                deleteActorList.invoke(list)
                _uiState.value = _uiState.value?.copy(
                    actorList = getActorList(showId),
                    messageWithUndo = stringProvider.getString(R.string.deleted)
                )
            } catch (e: Exception) {
                Timber.e(e.message, e)
                dataBaseError()
            }
        }
    }

    private fun insertActor(actorList: List<Actor>, showId: Long) {
        viewModelScope.launch {
            try {
                insertActorByShowId.invoke(actorList)
                _uiState.value = _uiState.value?.copy(
                    actorList = getActorList(showId),
                    message = stringProvider.getString(R.string.undone),
                )
            } catch (e: Exception) {
                Timber.e(e.message, e)
                dataBaseError()
            }
        }
    }

    private fun getActorList(showId: Long): List<Actor> {
        return if (showId == Constants.DEFAULT_SHOW_ID) {
            getAll()
        } else {
            getByShow(showId)
        }
    }
}