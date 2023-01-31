package com.example.showtracker.ui.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.showtracker.R
import com.example.showtracker.databinding.FragmentShowsBinding
import com.example.showtracker.domain.model.*
import com.example.showtracker.ui.common.Constants
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowsFragment : Fragment() {
    private var _binding: FragmentShowsBinding? = null
    private val binding get() = _binding!!
    lateinit var showAdapter: ShowAdapter
    private lateinit var actionMode: ActionMode

    private val callback by lazy {
        configContextBar()
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showAdapter = setShowAdapter()
        binding.rvShows.adapter = showAdapter

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            val showList = state.showList
            showAdapter.submitList(showList)
            checkMessage(state.message)
        }

        viewModel.handleEvent(MainEvent.InsertShowList(setInitialShows()))
        viewModel.handleEvent(MainEvent.GetAll)
    }

    private fun checkMessage(message: String?) {
        message?.let {
            Snackbar.make(
                requireActivity().findViewById(R.id.shows_layout),
                it,
                Snackbar.LENGTH_LONG
            )
                .show()
            viewModel.handleEvent(MainEvent.MessageShown)
        }
    }

    private fun setShowAdapter(): ShowAdapter {
        return ShowAdapter(
            activity?.applicationContext,
            object : ShowAdapter.ShowActions {
                override fun onClickShow(id: Long) {
                    val action = ShowsFragmentDirections.actionShowsToEpisodes(id)
                    findNavController().navigate(action)
                }

                override fun onStartSelectMode() {
                    (activity as AppCompatActivity).startSupportActionMode(callback)?.let {
                        actionMode = it
                        it.title = getString(R.string.one_selected)
                    }
                }

                override fun itemSelected(show: Show) {
                    actionMode.title =
                        showAdapter.getSelectedItems().size.toString() + getString(R.string.space) + getString(R.string.selected)
                }
            })
    }

    private fun configContextBar() =
        object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                (activity as AppCompatActivity).supportActionBar?.hide()
                activity?.menuInflater?.inflate(R.menu.menu_appbar_show, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                return setAppBarListeners(item)
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                (activity as AppCompatActivity).supportActionBar?.show()
                showAdapter.resetSelectMode()
            }

        }

    private fun setAppBarListeners(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.favorite -> {
                isFavorite(true)
            }
            R.id.unfavorite -> {
                isFavorite(false)
            }
            R.id.delete -> {
                viewModel.handleEvent(MainEvent.DeleteShowList(showAdapter.getSelectedItems()))
                finishActionMode()
                true
            }
            else -> false
        }
    }

    private fun isFavorite(isFavorite: Boolean): Boolean {
        showAdapter.getSelectedItems().forEach { it.isFavorite = isFavorite }
        viewModel.handleEvent(MainEvent.UpdateShowList(showAdapter.getSelectedItems()))
        finishActionMode()
        return true
    }

    private fun finishActionMode() {
        (activity as AppCompatActivity).supportActionBar?.show()
        showAdapter.resetSelectMode()
        actionMode.finish()
    }

    private fun setInitialShows(): ArrayList<Show> {
        val list: ArrayList<Show> = ArrayList()
        val episodeList1 = listOf(
            Episode(0, Constants.HERO_ACADEMY_CAP_1, 1, 1, Show(1), true),
            Episode(0, Constants.HERO_ACADEMY_CAP_2, 2, 1, Show(1), true),
            Episode(0, Constants.HERO_ACADEMY_CAP_3, 3, 1, Show(1), false),
        )
        val episodeList2 = listOf(
            Episode(0, Constants.PARKS_AND_RECS_CAP_1, 1, 1, Show(2), true),
            Episode(0, Constants.PARKS_AND_RECS_CAP_2, 2, 1, Show(2), false),
            Episode(0, Constants.PARKS_AND_RECS_CAP_3, 3, 1, Show(2), false),
        )

        val actorList1 = listOf(
            Actor(0, Constants.PARKS_AND_RECS_ACTOR_1, 51, listOf(Show(2))),
            Actor(0, Constants.PARKS_AND_RECS_ACTOR_2, 46, listOf(Show(2))),
            Actor(0, Constants.PARKS_AND_RECS_ACTOR_3, 52, listOf(Show(2)))
        )
        val actorList2 = listOf(
            Actor(0, Constants.AMERICAN_HORROR_STORY_ACTOR_1, 73, listOf(Show(3))),
            Actor(0, Constants.AMERICAN_HORROR_STORY_ACTOR_2, 35, listOf(Show(3))),
            Actor(0, Constants.AMERICAN_HORROR_STORY_ACTOR_3, 48, listOf(Show(3)))
        )

        list.addAll(
            listOf(
                Show(
                    1, Constants.HERO_ACADEMY, Genre.ANIME, StreamingService.CRUNCHYROLL,
                    true, episodeList1
                ), Show(
                    2, Constants.PARKS_AND_RECS, Genre.COMEDY, StreamingService.NETFLIX,
                    false, episodeList2, actorList1
                ), Show(
                    3, Constants.AMERICAN_HORROR_STORY, Genre.HORROR, StreamingService.DISNEY,
                    false, ArrayList(), actorList2
                )
            )
        )
        return list
    }
}