package com.example.showtracker.ui.seasondetails


sealed class SeasonDetailsEvent {
    class GetEpisodes(val tvId: Long, val seasonNumber: Int) : SeasonDetailsEvent()
    class GetActors(val tvId: Long, val seasonNumber: Int) : SeasonDetailsEvent()
    object MessageShown : SeasonDetailsEvent()
}
