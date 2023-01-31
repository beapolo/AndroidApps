package com.example.showtracker.utils

object SQLQueries {
    const val SELECT_SHOW_LIST = "Select * from show"
    const val SELECT_SHOW_WITH_EPISODES = "Select * from show s where show_id = :id"
    const val SELECT_ACTOR_LIST = "Select * from actor"
    const val SELECT_ACTOR_LIST_WITH_SHOWS = "Select * from actor a " +
            "inner join show_actor sa on sa.actor_id = a.actor_id " +
            "where show_id = :id"
    const val SELECT_SHOW_WITH_ACTOR = "Select * from show where show_id = :id"
    const val DELETE_EPISODE_LIST_BY_SHOW = "Delete from episode where show_child_id = :id"
    const val DELETE_SHOW_ACTOR_BY_SHOW = "Delete from show_actor where show_id = :id"
    const val DELETE_SHOW_ACTOR_BY_ACTOR = "Delete from show_actor where actor_id = :id"
}