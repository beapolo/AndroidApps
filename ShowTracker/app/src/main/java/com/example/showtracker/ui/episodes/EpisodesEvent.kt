package com.example.showtracker.ui.episodes

import com.example.showtracker.domain.model.Episode


sealed class EpisodesEvent {
    class GetShow(val id: Long) : EpisodesEvent()
    class InsertEpisode(val episode: Episode) : EpisodesEvent()
    class UpdateEpisode(val episode: Episode) : EpisodesEvent()
    class DeleteEpisode(val episode: Episode) : EpisodesEvent()
    object MessageShown : EpisodesEvent()
}
