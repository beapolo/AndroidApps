package com.example.showtracker.ui.showdetails

import com.example.showtracker.domain.model.Show

data class ShowDetailsState(
    val show: Show? = null,
    val message: String? = null,
    var isLoading: Boolean = false,
)