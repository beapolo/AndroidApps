package com.example.showtracker.ui.addactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showtracker.R
import com.example.showtracker.domain.model.Actor
import com.example.showtracker.domain.usecases.actors.DeleteActorUC
import com.example.showtracker.domain.usecases.actors.InsertActorUC
import com.example.showtracker.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddActorViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val insertActor: InsertActorUC,
    private val deleteActor: DeleteActorUC
) : ViewModel() {
    private val _uiState = MutableLiveData(AddActorState())
    val uiState: LiveData<AddActorState> get() = _uiState

    fun handleEvent(event: AddActorEvent) {
        when (event) {
            is AddActorEvent.Insert -> insertActor(event.actor)
            is AddActorEvent.Delete -> deleteActor(event.actor)
            AddActorEvent.MessageShown -> _uiState.value =
                _uiState.value?.copy(messageWithUndo = null, message = null)
        }
    }

    private fun insertActor(actor: Actor) {
        viewModelScope.launch {
            try {
                val result = insertActor.invoke(actor)
                if (result > 0) {
                    _uiState.value = _uiState.value?.copy(
                        messageWithUndo = stringProvider.getString(R.string.saved),
                    )
                } else if (result == com.example.showtracker.domain.model.Error.FILL_OPTIONS.error){
                    _uiState.value = _uiState.value?.copy(
                        message = stringProvider.getString(R.string.fill_fields)
                    )
                } else {
                    _uiState.value = _uiState.value?.copy(
                        message = stringProvider.getString(R.string.error)
                    )
                }
            } catch (e: Exception) {
                Timber.e(e.message, e)
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.error)
                )
            }
        }
    }

    private fun deleteActor(actor: Actor) {
        viewModelScope.launch {
            try {
                deleteActor.invoke(actor)
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.undone)
                )
            } catch (e: Exception) {
                Timber.e(e.message, e)
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.error)
                )
            }
        }
    }
}
