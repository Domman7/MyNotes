package com.example.mynotes.presentation.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentNotesBinding
import com.example.mynotes.domain.NoteEntity
import com.example.mynotes.domain.toDomain
import com.example.mynotes.presentation.adapters.NotesAdapter
import com.example.mynotes.presentation.util.launchWhenStarted
import com.example.mynotes.presentation.viewmodels.NotesFragmentViewModel
import com.example.mynotes.utils.snack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class NotesFragment : Fragment(), CoroutineScope by MainScope() {


    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.notes.onEach {
            val noteAdapter = NotesAdapter(
                notes = it.map { entity -> entity.toDomain() }
            )
            binding.recyclerViewNotes.adapter = noteAdapter

            noteAdapter.setOnClickListener(object :
                NotesAdapter.OnClickListener {
                override fun onClick(position: Int, model: NoteEntity) {

                    val dialogBuilder = AlertDialog.Builder(activity!!)
                    dialogBuilder.setMessage("Delete note?")
                        .setCancelable(true)
                        .setPositiveButton("Ok", DialogInterface.OnClickListener {
                                _, _ ->
                            viewModel.deleteNoteById(
                                id = model.id,
                            ).run {
                                binding.root.snack(
                                    string = R.string.successfully_deleted
                                )}
                        })
                    val alert = dialogBuilder.create()
                    alert.setTitle("Deleting note")
                    alert.show()
                }
            })
        }.launchWhenStarted(lifecycleScope = lifecycleScope)

        binding.btnCreateNote.setOnClickListener {
            val fragment = CreateNoteFragment()
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}