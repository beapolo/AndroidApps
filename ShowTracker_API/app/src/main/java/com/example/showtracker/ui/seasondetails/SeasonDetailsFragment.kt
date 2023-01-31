package com.example.showtracker.ui.seasondetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.showtracker.R
import com.example.showtracker.databinding.FragmentSeasonDetailsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SeasonDetailsFragment : Fragment() {
    private var _binding: FragmentSeasonDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var actorAdapter: ActorAdapter

    private val viewModel: SeasonDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeasonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        episodeAdapter = EpisodeAdapter()
        actorAdapter = ActorAdapter()
        binding.rvEpisodes.adapter = episodeAdapter
        binding.rvActors.adapter = actorAdapter
        val args: SeasonDetailsFragmentArgs by navArgs()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    binding.loading.visibility = if (value.isLoading) View.VISIBLE else View.GONE
                    episodeAdapter.submitList(value.episodes)
                    actorAdapter.submitList(value.actors)
                    checkMessage(value.message)
                }
            }
        }

        viewModel.handleEvent(SeasonDetailsEvent.GetEpisodes(args.showId,args.seasonNumber.toInt()))
        viewModel.handleEvent(SeasonDetailsEvent.GetActors(args.showId, args.seasonNumber.toInt()))
    }

    private fun checkMessage(message: String?) {
        message?.let {
            Snackbar.make(
                requireActivity().findViewById(R.id.season_details_layout),
                it,
                Snackbar.LENGTH_LONG
            )
                .show()
            viewModel.handleEvent(SeasonDetailsEvent.MessageShown)
        }
    }
}