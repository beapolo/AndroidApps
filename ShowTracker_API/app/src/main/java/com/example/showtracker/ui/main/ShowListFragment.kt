package com.example.showtracker.ui.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.showtracker.R
import com.example.showtracker.databinding.FragmentShowsBinding
import com.example.showtracker.domain.model.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowListFragment : Fragment() {
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

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    binding.loading.visibility = if (value.isLoading) View.VISIBLE else View.GONE
                    showAdapter.submitList(value.showList)
                    checkMessage(value.message)
                }
            }
        }
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
                    val action = ShowListFragmentDirections.showListToShowDetails(id)
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
                        showAdapter.getSelectedItems().size.toString() + getString(R.string.space) + getString(
                            R.string.selected
                        )
                }
            })
    }

    private fun configContextBar() =
        object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                (activity as AppCompatActivity).supportActionBar?.hide()
                activity?.menuInflater?.inflate(R.menu.menu_appbar_favorite, menu)
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
                val list = showAdapter.getSelectedItems()
                list.forEach { it.isFavorite = true }
                viewModel.handleEvent(MainEvent.SaveShowListAsFavorites(list))
                finishActionMode()
                return true
            }
            else -> false
        }
    }

    private fun finishActionMode() {
        (activity as AppCompatActivity).supportActionBar?.show()
        showAdapter.resetSelectMode()
        actionMode.finish()
    }
}