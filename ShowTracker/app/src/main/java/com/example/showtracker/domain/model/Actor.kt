package com.example.showtracker.domain.model

data class Actor(
    var id: Long = 0,
    val name: String? = null,
    val age: Int? = null,
    var showList: List<Show> = ArrayList(),

    var isSelected: Boolean = false,
)
