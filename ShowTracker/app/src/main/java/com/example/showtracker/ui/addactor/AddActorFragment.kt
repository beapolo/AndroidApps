package com.example.showtracker.ui.addactor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.showtracker.R
import com.example.showtracker.databinding.FragmentAddActorBinding
import com.example.showtracker.domain.model.Actor
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddActorFragment : Fragment() {
    private var _binding: FragmentAddActorBinding? = null
    private val binding get() = _binding!!

    private lateinit var actor: Actor

    private val viewModel: AddActorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddActorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            showMessageWithUndo(state.messageWithUndo)
            showMessage(state.message)
        }

        setListeners()
    }

    private fun setListeners() {
        with(binding) {
            addActorBtn.setOnClickListener {
                val name = name.editText?.text.toString()
                val ageText = age.editText?.text
                var age: Int? = null
                if (ageText?.isEmpty() == false) {
                    age = ageText.toString().toInt()
                }
                actor = Actor(0, name, age)
                viewModel.handleEvent(AddActorEvent.Insert(actor))
            }
        }
    }

    private fun showMessageWithUndo(message: String?) {
        message?.let {
            Snackbar.make(
                requireActivity().findViewById(R.id.add_actor_layout),
                it,
                Snackbar.LENGTH_LONG
            )
                .setAction(getString(R.string.undo)) {
                    viewModel.handleEvent(AddActorEvent.Delete(actor))
                }
                .show()
            viewModel.handleEvent(AddActorEvent.MessageShown)
        }
    }

    private fun showMessage(message: String?) {
        message?.let {
            Snackbar.make(
                requireActivity().findViewById(R.id.add_actor_layout),
                it,
                Snackbar.LENGTH_LONG
            )
                .show()
            viewModel.handleEvent(AddActorEvent.MessageShown)
        }
    }
}