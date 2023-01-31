package com.example.showtracker.data.common

object Constants {
    //MESSAGES
    const val FAVORITE_FAILED = "Failed to save as Favorite"
    const val UNFAVORITE_FAILED = "Failed to unmark as favorite"

    //RETROFIT
    const val BASE_URL = "https://api.themoviedb.org"
    const val IMG_BASE_URL = "https://image.tmdb.org/t/p/w500"
    const val API_KEY_NAME = "api_key"

    //API PATHS
    const val POPULAR_SHOWS_PATH = "/3/tv/popular"
    const val SINGLE_SHOW_PATH = "/3/tv/{tv_id}"
    const val CREDITS_PATH = "/3/tv/{tv_id}/season/{season_number}/credits"
    const val EPISODES_PATH = "/3/tv/{tv_id}/season/{season_number}"

    //API PARAMS
    const val PARAM_TV_ID = "tv_id"
    const val PARAM_SEASON_NUMBER = "season_number"

    //DATABASE
    const val DATABASE = "database/show_tracker.db"
    const val DATABASE_NAME = "item_database"
    const val ASSET = "assetDB"

    //TABLES
    const val TABLE_SHOW = "show"
    const val TABLE_SEASON = "season"

    //ROOM COLUMNS
    const val COLUMN_SHOW_ID = "show_id"
    const val COLUMN_SHOW_CHILD_ID = "show_child_id"
    const val COLUMN_EPISODE_COUNT = "episode_count"
    const val COLUMN_SEASON_ID = "season_id"
    const val COLUMN_SHOW_TITLE = "show_title"
    const val COLUMN_STREAMING_SERVICE = "streaming_service"
    const val COLUMN_FAVORITE = "favorite"

    //API COLUMNS
    const val COLUMN_CREDIT_ID = "credit_id"
    const val COLUMN_DEPARTMENT = "known_for_department"
    const val COLUMN_ORIGINAL_NAME = "original_name"
    const val COLUMN_ORIGINAL_LANGUAGE = "original_language"
    const val COLUMN_ORIGIN_COUNTRY = "origin_country"
    const val COLUMN_PROFILE_PATH = "profile_path"
    const val COLUMN_AIR_DATE = "air_date"
    const val COLUMN_LAST_AIR_DATE = "last_air_date"
    const val COLUMN_SEASON_NUMBER = "season_number"
    const val COLUMN_STRING_ID = "_id"
    const val COLUMN_POSTER_PATH = "poster_path"
    const val COLUMN_PRODUCTION_CODE = "production_code"
    const val COLUMN_PRODUCTION_COUNTRIES = "production_countries"
    const val COLUMN_PRODUCTION_COMPANIES = "production_companies"
    const val COLUMN_STILL_PATH = "still_path"
    const val COLUMN_GUEST_STARS = "guest_stars"
    const val COLUMN_EPISODE_NUMBER = "episode_number"
    const val COLUMN_NUMBER_OF_EPISODES = "number_of_episodes"
    const val COLUMN_NUMBER_OF_SEASONS = "number_of_seasons"
    const val COLUMN_VOTE_AVERAGE = "vote_average"
    const val COLUMN_VOTE_COUNT = "vote_count"
    const val COLUMN_TOTAL_PAGES = "total_pages"
    const val COLUMN_TOTAL_RESULTS = "total_results"
    const val COLUMN_FIRST_AIR_DATE = "first_air_date"
    const val COLUMN_GENRE_IDS = "genre_ids"
    const val COLUMN_BACKDROP_PATH = "backdrop_path"
    const val COLUMN_LOGO_PATH = "logo_path"
    const val COLUMN_CREATED_BY = "created_by"
    const val COLUMN_LAST_EPISODE = "last_episode_to_air"
    const val COLUMN_NEXT_EPISODE = "next_episode_to_air"
    const val COLUMN_SPOKEN_LANGUAGES = "spoken_languages"
    const val COLUMN_EPISODE_TIME = "episode_run_time"
    const val COLUMN_IN_PRODUCTION = "in_production"
    const val COLUMN_ISO_3166_1 = "iso_3166_1"
    const val COLUMN_ISO_639_1 = "iso_639_1"
    const val COLUMN_ENGLISH = "english_name"
}