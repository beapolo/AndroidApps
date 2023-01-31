package com.example.showtracker.ui.addshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showtracker.R
import com.example.showtracker.domain.model.Show
import com.example.showtracker.domain.usecases.shows.DeleteShowUC
import com.example.showtracker.domain.usecases.shows.InsertShowUC
import com.example.showtracker.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddShowViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val insertShow: InsertShowUC,
    private val deleteShow: DeleteShowUC,
) : ViewModel() {
    private val _uiState = MutableLiveData(AddShowState())
    val uiState: LiveData<AddShowState> get() = _uiState

    fun handleEvent(event: AddShowEvent) {
        when (event) {
            is AddShowEvent.Insert -> insertShow(event.show)
            is AddShowEvent.Delete -> deleteShow(event.show)
            AddShowEvent.MessageShown -> _uiState.value =
                _uiState.value?.copy(messageWithUndo = null, message = null)
        }
    }

    private fun insertShow(show: Show) {
        viewModelScope.launch {
            try {
                val result = insertShow.invoke(show)
                if (result > 0) {
                    _uiState.value = _uiState.value?.copy(
                        messageWithUndo = stringProvider.getString(R.string.saved),
                    )
                } else if (result == com.example.showtracker.domain.model.Error.FILL_OPTIONS.error) {
                    _uiState.value = _uiState.value?.copy(
                        message = stringProvider.getString(R.string.fill_fields)
                    )
                } else if (result == com.example.showtracker.domain.model.Error.REPEATED_NAME.error){
                    _uiState.value = _uiState.value?.copy(
                        message = stringProvider.getString(R.string.repeated_title)
                    )
                } else {
                    setError()
                }
            } catch (e: Exception) {
                Timber.e(e.message, e)
                setError()
            }
        }
    }

    private fun deleteShow(show: Show) {
        viewModelScope.launch {
            try {
                deleteShow.invoke(show)
                _uiState.value = _uiState.value?.copy(
                    message = stringProvider.getString(R.string.undone)
                )
            } catch (e: Exception) {
                Timber.e(e.message, e)
                setError()
            }
        }
    }

    private fun setError(){
        _uiState.value = _uiState.value?.copy(
            message = stringProvider.getString(R.string.error)
        )
    }
}
