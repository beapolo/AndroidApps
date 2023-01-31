package com.example.showtracker.ui.main

import com.example.showtracker.domain.model.Show

sealed class MainEvent {
    object GetAll : MainEvent()
    class InsertShowList(val list: ArrayList<Show>) : MainEvent()
    class UpdateShowList(val list: List<Show>) : MainEvent()
    class DeleteShowList(val list: List<Show>) : MainEvent()
    object MessageShown : MainEvent()

}