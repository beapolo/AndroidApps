package com.example.showtracker.ui.addshow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.viewModels
import com.example.showtracker.R
import com.example.showtracker.databinding.FragmentAddShowBinding
import com.example.showtracker.domain.model.Genre
import com.example.showtracker.domain.model.Show
import com.example.showtracker.domain.model.StreamingService
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddShowFragment : Fragment() {
    private var _binding: FragmentAddShowBinding? = null
    private val binding get() = _binding!!

    private lateinit var show: Show

    private val viewModel: AddShowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            checkMessageWithUndo(state.messageWithUndo)
            checkMessage(state.message)
        }

        setShowOptionLists()
        setListeners()
    }

    private fun checkMessageWithUndo(message: String?) {
        message?.let {
            Snackbar.make(
                requireActivity().findViewById(R.id.show_layout),
                it,
                Snackbar.LENGTH_LONG
            )
                .setAction(getString(R.string.undo)) {
                    viewModel.handleEvent(AddShowEvent.Delete(show))
                }
                .show()
            viewModel.handleEvent(AddShowEvent.MessageShown)
        }
    }

    private fun checkMessage(message: String?) {
        message?.let {
            Snackbar.make(
                requireActivity().findViewById(R.id.show_layout),
                it,
                Snackbar.LENGTH_LONG
            )
                .show()
            viewModel.handleEvent(AddShowEvent.MessageShown)
        }
    }

    private fun setListeners() {
        with(binding) {
            addShowBtn.setOnClickListener {
                val title = title.editText?.text.toString()
                val genre = getRadioBtnText(groupGenre)
                val streamingService = getRadioBtnText(groupStreamingServ)

                show =
                    Show(
                        0,
                        title,
                        genre?.let { it1 -> Genre.valueOf(it1) },
                        streamingService?.let { it1 -> StreamingService.valueOf(it1) }
                    )
                viewModel.handleEvent(AddShowEvent.Insert(show))
            }
        }
    }

    private fun getRadioBtnText(group: RadioGroup): String? {
        val text: String?
        val id = group.checkedRadioButtonId

        text = if (id == com.example.showtracker.domain.model.Error.WRONG_ID.error.toInt()) {
            null
        } else {
            val rbtn: RadioButton? = activity?.findViewById(id)
            rbtn?.text.toString()
        }

        return text
    }

    private fun setShowOptionLists() {
        with(binding) {
            var id = 1
            val genreList: List<String> = Genre.values().map { it.name }
            id = addRadioBtns(genreList, groupGenre, id)

            val streamingServList: List<String> = StreamingService.values().map { it.name }
            addRadioBtns(streamingServList, groupStreamingServ, id)
        }
    }

    private fun addRadioBtns(list: List<String>, radioGroup: RadioGroup, firstId: Int): Int {
        var id = firstId
        list.forEach {
            val radioBtn = RadioButton(this@AddShowFragment.context)
            radioBtn.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            radioBtn.text = it
            radioBtn.id = id
            id += 1

            radioGroup.addView(radioBtn)
        }
        return id
    }
}