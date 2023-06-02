package com.example.mynotes.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.data.local.MyNotesDatabase
import com.example.mynotes.domain.NoteEntity
import com.example.mynotes.domain.toLocal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesFragmentViewModel @Inject constructor(
    private val db: MyNotesDatabase
) : ViewModel() {

    val notes = db.noteDao.getAllNotes()

    fun insertNote(
        name: String,
        text: String,
        validUntil: Long,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val transaction = NoteEntity(
                id = 0,
                name = name,
                text = text,
                createdAt = System.currentTimeMillis(),
                validUntil = validUntil,
            )
            val id = db.noteDao.insertNote(transaction.toLocal())
        }
    }
    fun deleteNoteById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            db.noteDao.deleteNoteByID(id)
        }
    }
}