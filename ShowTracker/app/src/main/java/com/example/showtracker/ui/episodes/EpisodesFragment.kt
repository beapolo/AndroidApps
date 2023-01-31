package com.example.showtracker.ui.episodes

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.showtracker.R
import com.example.showtracker.databinding.EpisodeAddDialogBinding
import com.example.showtracker.databinding.FragmentEpisodesBinding
import com.example.showtracker.domain.model.Episode
import com.example.showtracker.domain.model.Show
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodesFragment : Fragment() {
    private var _binding: FragmentEpisodesBinding? = null
    private val binding get() = _binding!!
    private lateinit var bindingDialog: EpisodeAddDialogBinding
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var show: Show
    private lateinit var episode: Episode

    private val viewModel: EpisodesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        episodeAdapter = setEpisodeAdapter()
        binding.rvEpisodes.adapter = episodeAdapter
        val args: EpisodesFragmentArgs by navArgs()

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            show = state.show
            episodeAdapter.submitList(show.episodeList)
            setShowData()
            showAddDialog(state.dialogMessage)
            showMessageWithUndo(state.messageWithUndo)
            showMessage(state.message)
        }

        setListeners()
        viewModel.handleEvent(EpisodesEvent.GetShow(args.showId))
    }

    private fun setEpisodeAdapter(): EpisodeAdapter {
        return EpisodeAdapter(
            object : EpisodeAdapter.EpisodeActions {
                override fun changeVisibility(episode: Episode, position: Int) {
                    episode.isWatched = !episode.isWatched
                    episodeAdapter.notifyItemChanged(position)
                    viewModel.handleEvent(EpisodesEvent.UpdateEpisode(episode))
                }
            })
    }

    private fun setShowData() {
        with(binding) {
            showName.text = show.title
        }
    }

    private fun showMessageWithUndo(message: String?) {
        message?.let {
            Snackbar.make(
                requireActivity().findViewById(R.id.episodes_layout),
                it,
                Snackbar.LENGTH_LONG
            )
                .setAction(getString(R.string.undo)) {
                    viewModel.handleEvent(EpisodesEvent.DeleteEpisode(episode))
                }
                .show()
            viewModel.handleEvent(EpisodesEvent.MessageShown)
        }
    }

    private fun showMessage(message: String?) {
        message?.let {
            Snackbar.make(
                requireActivity().findViewById(R.id.episodes_layout),
                it,
                Snackbar.LENGTH_LONG
            )
                .show()
            viewModel.handleEvent(EpisodesEvent.MessageShown)
        }
    }

    private fun setListeners() {
        with(binding) {
            seeCast.setOnClickListener {
                val action = EpisodesFragmentDirections.actionEpisodesToActors(show.id)
                findNavController().navigate(action)
            }

            addBtn.setOnClickListener {
                showAddDialog(getString(R.string.empty))
            }
        }
    }

    private fun showAddDialog(message: String?) {
        if (message != null) {
            val li = LayoutInflater.from(this.context)
            val dialogView: View = li.inflate(R.layout.episode_add_dialog, null)
            bindingDialog = EpisodeAddDialogBinding.bind(dialogView)

            (dialogView.parent as? ViewGroup)?.removeView(dialogView)
            this.context?.let {
                MaterialAlertDialogBuilder(it)
                    .setView(dialogView)
                    .setTitle(getString(R.string.add_episode))
                    .setMessage(message)
                    .setNegativeButton(getString(R.string.cancel)) { view, _ ->
                        view.dismiss()
                    }
                    .setPositiveButton(getString(R.string.add)) { _, _ ->
                        addEpisode()
                    }.show()
            }
            viewModel.handleEvent(EpisodesEvent.MessageShown)
        }
    }

    private fun addEpisode() {
        with(bindingDialog) {
            val title = epTitle.editText?.text.toString()
            val season = isInteger(season.editText?.text)
            val episodeNumber: Int? = isInteger(epNumber.editText?.text)

            episode = Episode(0, title, episodeNumber, season, show, false)
            viewModel.handleEvent(EpisodesEvent.InsertEpisode(episode))
        }
    }

    private fun isInteger(text: Editable?): Int? {
        var number: Int? = null
        if (text?.isEmpty() == false) {
            number = text.toString().toInt()
        }
        return number
    }

}