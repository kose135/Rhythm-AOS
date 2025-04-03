package com.longlegsdev.rhythm.di

import android.content.Context
import androidx.room.Room
import com.longlegsdev.rhythm.data.local.NoteDatabase
import com.longlegsdev.rhythm.util.Rhythm
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext app: Context,
    ): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            Rhythm.DATABASE_NAME
        ).run {
            build()
        }
    }


}