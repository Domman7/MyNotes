package com.example.mynotes.domain

fun com.example.mynotes.data.local.entity.NoteEntity.toDomain(): NoteEntity {
    return NoteEntity(
        id = this.id,
        name = this.name,
        text = this.text,
        createdAt = this.createdAt,
        validUntil = this.validUntil,
    )
}

fun NoteEntity.toLocal(): com.example.mynotes.data.local.entity.NoteEntity {
    return com.example.mynotes.data.local.entity.NoteEntity(
        id = this.id,
        name = this.name,
        text = this.text,
        createdAt = this.createdAt,
        validUntil = this.validUntil,
    )
}