package com.example.mynotes.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var name: String,
    var text: String,
    val createdAt: Long,
    val validUntil: Long,
)