package com.example.showtracker.ui.showdetails

sealed class ShowDetailsEvent {
    class GetShow(val id: Long) : ShowDetailsEvent()
    object MessageShown : ShowDetailsEvent()
}