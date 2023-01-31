package com.example.showtracker.ui.main

import com.example.showtracker.domain.model.Show

data class MainState(
    val showList: List<Show?>? = ArrayList(),
    val message: String? = null,
    var isLoading: Boolean = false,
)