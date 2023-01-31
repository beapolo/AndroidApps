package com.example.showtracker.ui.favoriteshows

import com.example.showtracker.domain.model.Show

sealed class FavoriteShowListEvent {
    object GetFavoriteShowList : FavoriteShowListEvent()
    class RemoveShowListFromFavorites(val list: List<Show>) : FavoriteShowListEvent()
    object MessageShown : FavoriteShowListEvent()
}