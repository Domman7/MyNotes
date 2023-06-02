package com.example.mynotes.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentCreateNoteBinding
import com.example.mynotes.presentation.viewmodels.NotesFragmentViewModel
import com.example.mynotes.utils.snack
import com.example.mynotes.utils.transformIntoDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class CreateNoteFragment : Fragment() {

    private var _binding: FragmentCreateNoteBinding? = null
    private val binding: FragmentCreateNoteBinding get() = _binding!!
    private val viewModel: NotesFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            etWhen.transformIntoDatePicker(
                requireContext(),
                "dd/MM/yyyy",
                Date(),
            )

            btnSaveNote.setOnClickListener {
                binding.apply {
                    val title = binding.etTitle.text.toString()
                    val text = binding.etText.text.toString()
                    val date = binding.etWhen.text.toString()

                    when {
                        title.isEmpty() -> {
                            this.etTitle.error = "Title must not be empty"
                        }
                        date.isEmpty() -> {
                            this.etWhen.error = "Date must not be empty"
                        }
                        else -> {
                            viewModel.insertNote(
                                name = title,
                                text = text,
                                validUntil = SimpleDateFormat("dd/MM/yyyy").parse(date).time,
                            ).run {
                                binding.root.snack(
                                    string = R.string.successfully_saved
                                )

                                val fragment = NotesFragment()
                                parentFragmentManager
                                    .beginTransaction()
                                    .replace(R.id.fragment_container_view, fragment)
                                    .commit()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}