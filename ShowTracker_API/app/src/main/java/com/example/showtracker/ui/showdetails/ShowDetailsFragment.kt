package com.example.showtracker.ui.showdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.showtracker.R
import com.example.showtracker.databinding.FragmentShowDetailsBinding
import com.example.showtracker.domain.model.Show
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowDetailsFragment : Fragment() {
    private var _binding: FragmentShowDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var seasonAdapter: SeasonAdapter
    var show: Show = Show()

    private val viewModel: ShowDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seasonAdapter = setSeasonAdapter()
        binding.rvSeason.adapter = seasonAdapter
        val args: ShowDetailsFragmentArgs by navArgs()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    binding.loading.visibility = if (value.isLoading) View.VISIBLE else View.GONE
                    if (value.show != null) {
                        show = value.show
                    }
                    setShowInfo(show)

                    seasonAdapter.submitList(show.seasonList)
                    checkMessage(value.message)
                }
            }
        }

        viewModel.handleEvent(ShowDetailsEvent.GetShow(args.showId))
    }

    private fun setShowInfo(show: Show?) {
        with(binding) {
            showImg.load(show?.img)
            networkLogo.load(show?.network)
            overview.text = show?.overview
        }
    }

    private fun checkMessage(message: String?) {
        message?.let {
            Snackbar.make(
                requireActivity().findViewById(R.id.show_detail_layout),
                it,
                Snackbar.LENGTH_LONG
            )
                .show()
            viewModel.handleEvent(ShowDetailsEvent.MessageShown)
        }
    }

    private fun setSeasonAdapter(): SeasonAdapter {
        return SeasonAdapter(
            object : SeasonAdapter.SeasonActions {
                override fun onClickShowSeason(seasonNumber: Int) {
                    val action =
                        ShowDetailsFragmentDirections.showDetailsToSeasonDetails(show.id, seasonNumber.toLong())
                    findNavController().navigate(action)
                }
            }
        )
    }

}