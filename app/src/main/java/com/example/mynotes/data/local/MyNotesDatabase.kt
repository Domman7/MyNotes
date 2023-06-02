package com.example.mynotes.data.local

import android.content.Context
import androidx.room.*
import com.example.mynotes.data.local.dao.NoteDao
import com.example.mynotes.data.local.entity.NoteEntity


@Database(
    entities = [
        NoteEntity::class
    ],
    version = 1
)
abstract class MyNotesDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "myNotes_db"

        @Volatile private var instance: MyNotesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            MyNotesDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}