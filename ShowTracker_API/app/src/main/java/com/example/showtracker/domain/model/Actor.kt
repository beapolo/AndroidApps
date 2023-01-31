package com.example.showtracker.domain.model

import android.net.Uri

data class Actor(
    var id: Long = 0,
    val name: String? = null,
    val character: String? = null,
    val age: Int? = null,
    var showList: List<Show> = ArrayList(),
    var img: Uri? = null,

    var isSelected: Boolean = false,
)
