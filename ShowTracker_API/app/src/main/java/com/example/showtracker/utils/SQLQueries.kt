package com.example.showtracker.utils

object SQLQueries {
    const val SELECT_FAVORITE_SHOW_LIST = "Select * from show where favorite = 1"
    const val SELECT_SHOW = "Select * from show where show_id = :id"
    const val DELETE_SEASON_LIST_BY_SHOW = "Delete from season where show_child_id = :id"
}