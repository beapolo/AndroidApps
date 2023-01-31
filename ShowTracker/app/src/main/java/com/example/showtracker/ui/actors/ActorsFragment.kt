package com.example.showtracker.ui.actors

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.showtracker.R
import com.example.showtracker.databinding.FragmentActorsBinding
import com.example.showtracker.domain.model.Actor
import com.example.showtracker.ui.common.Constants
import com.example.showtracker.ui.main.ShowsFragmentDirections
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class ActorsFragment : Fragment() {
    private var _binding: FragmentActorsBinding? = null
    private val binding get() = _binding!!
    private lateinit var actorAdapter: ActorAdapter
    private lateinit var actorList: List<Actor>
    private var showId by Delegates.notNull<Long>()
    private lateinit var actionMode: ActionMode

    private val callback by lazy {
        configContextBar()
    }

    private val viewModel: ActorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actorAdapter = setActorAdapter()
        binding.rvActors.adapter = actorAdapter

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            val list = state.actorList
            hasActors(list)
            actorAdapter.submitList(list)
            showMessageWithUndo(state.messageWithUndo)
            showMessage(state.message)
        }

        val args: ActorsFragmentArgs by navArgs()
        showId = args.showId
        if (showId == Constants.DEFAULT_SHOW_ID) {
            viewModel.handleEvent(ActorEvent.GetAll)
        } else {
            viewModel.handleEvent(ActorEvent.GetByShow(showId))
        }
    }

    private fun hasActors(list: List<Actor>) {
        if (list.isEmpty()) {
            binding.message.visibility = View.VISIBLE
        } else {
            binding.message.visibility = View.INVISIBLE
        }
    }

    private fun setActorAdapter(): ActorAdapter {
        return ActorAdapter(
            object : ActorAdapter.ActorActions {
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

                override fun itemSelected(actor: Actor) {
                    actionMode.title =
                        actorAdapter.getSelectedItems().size.toString() +
                                getString(R.string.space) + getString(R.string.selected)
                }
            })
    }

    private fun showMessageWithUndo(message: String?) {
        message?.let { it ->
            Snackbar.make(
                requireActivity().findViewById(R.id.actor_layout),
                it,
                Snackbar.LENGTH_LONG
            )
                .setAction(getString(R.string.undo)) {
                    viewModel.handleEvent(ActorEvent.InsertActorList(actorList, showId))

                }
                .show()
            viewModel.handleEvent(ActorEvent.MessageShown)
        }
    }

    private fun showMessage(message: String?) {
        message?.let {
            Snackbar.make(
                requireActivity().findViewById(R.id.actor_layout),
                it,
                Snackbar.LENGTH_LONG
            )
                .show()
            viewModel.handleEvent(ActorEvent.MessageShown)
        }
    }

    private fun configContextBar() =
        object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                (activity as AppCompatActivity).supportActionBar?.hide()
                activity?.menuInflater?.inflate(R.menu.menu_appbar_actor, menu)
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
                actorAdapter.resetSelectMode()
            }

        }

    private fun setAppBarListeners(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete -> {
                actorList = actorAdapter.getSelectedItems()
                viewModel.handleEvent(
                    ActorEvent.DeleteActorList(actorList, showId)
                )
                finishActionMode()
                true
            }
            else -> false
        }
    }

    private fun finishActionMode() {
        (activity as AppCompatActivity).supportActionBar?.show()
        actorAdapter.resetSelectMode()
        actionMode.finish()
    }
}