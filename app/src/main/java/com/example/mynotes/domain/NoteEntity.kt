package com.example.mynotes.domain

import java.util.*

data class NoteEntity(
    val id: Int,
    val name: String,
    val text: String,
    val createdAt: Long,
    val validUntil: Long,
)