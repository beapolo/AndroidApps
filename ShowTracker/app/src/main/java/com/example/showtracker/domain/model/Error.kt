package com.example.showtracker.domain.model

enum class Error(val error: Long) {
    WRONG_ID(-1),
    WRONG_EPISODE_NUMBER(-2),
    FILL_OPTIONS(-3),
    REPEATED_NAME(-4);
}