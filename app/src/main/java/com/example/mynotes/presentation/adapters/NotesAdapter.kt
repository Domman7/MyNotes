package com.example.mynotes.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.databinding.RecyclerViewNoteListItemBinding
import com.example.mynotes.domain.NoteEntity
import com.example.mynotes.presentation.extensions.dateToString
import java.util.Date

class NotesAdapter(
    private val notes: List<NoteEntity>
) : RecyclerView.Adapter<NotesAdapter.ViewHolder>()
{
    private var onClickListener: OnClickListener? = null

    class ViewHolder(val binding: RecyclerViewNoteListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerViewNoteListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val note = notes[position]

            tvDate.text = Date(note.createdAt).dateToString("dd MMMM")
            tvUntilDate.text = Date(note.validUntil).dateToString("dd MMMM")
            tvTitle.text = note.name
            tvText.text = note.text

            holder.itemView.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(position, note)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return notes.count()
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: NoteEntity)
    }

}