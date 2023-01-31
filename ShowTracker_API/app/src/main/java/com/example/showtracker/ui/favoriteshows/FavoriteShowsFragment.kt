package com.example.showtracker.ui.favoriteshows

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.showtracker.R
import com.example.showtracker.databinding.FragmentFavoriteShowsBinding
import com.example.showtracker.domain.model.Show
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteShowsFragment : Fragment() {
    private var _binding: FragmentFavoriteShowsBinding? = null
    private val binding get() = _binding!!
    lateinit var showAdapter: FavoriteShowAdapter
    private lateinit var actionMode: ActionMode

    private val callback by lazy {
        configContextBar()
    }

    private val viewModel: FavoriteShowListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showAdapter = setShowAdapter()
        binding.rvFavoriteShows.adapter = showAdapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    binding.loading.visibility = if (value.isLoading) View.VISIBLE else View.GONE
                    checkShowList(value.showList)
                    checkMessage(value.message)
                }
            }
        }
        viewModel.handleEvent(FavoriteShowListEvent.GetFavoriteShowList)
    }

    private fun checkShowList(list: List<Show>?) {
        if (list?.isEmpty() == true) {
            binding.txtEmptyList.visibility = View.VISIBLE
        } else {
            binding.txtEmptyList.visibility = View.GONE
        }
        showAdapter.submitList(list)
    }

    private fun checkMessage(message: String?) {
        message?.let {
            Snackbar.make(
                requireActivity().findViewById(R.id.favorite_shows_layout),
                it,
                Snackbar.LENGTH_LONG
            )
                .show()
            viewModel.handleEvent(FavoriteShowListEvent.MessageShown)
        }
    }

    private fun setShowAdapter(): FavoriteShowAdapter {
        return FavoriteShowAdapter(
            activity?.applicationContext,
            object : FavoriteShowAdapter.ShowActions {
                override fun onClickShow(id: Long) {
                    val action = FavoriteShowsFragmentDirections.favoriteShowsToShowDetails(id)
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
                activity?.menuInflater?.inflate(R.menu.menu_appbar_unfavorite, menu)
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
            R.id.unfavorite -> {
                val list = showAdapter.getSelectedItems()
                list.forEach { it.isFavorite = false }
                viewModel.handleEvent(FavoriteShowListEvent.RemoveShowListFromFavorites(list))
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