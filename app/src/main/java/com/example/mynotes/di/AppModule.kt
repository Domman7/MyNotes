package com.example.mynotes.di

import android.app.Application
import androidx.room.Room
import com.example.mynotes.data.local.MyNotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideMyNotesDatabase(app: Application): MyNotesDatabase {
        return Room.databaseBuilder(
            app,
            MyNotesDatabase::class.java,
            MyNotesDatabase.DATABASE_NAME,
        ).build()
    }
}