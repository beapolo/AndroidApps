package com.example.showtracker.ui.main

import com.example.showtracker.domain.model.Show

sealed class MainEvent {
    object GetAll : MainEvent()
    class SaveShowListAsFavorites(val list: List<Show>) : MainEvent()
    object MessageShown : MainEvent()

}