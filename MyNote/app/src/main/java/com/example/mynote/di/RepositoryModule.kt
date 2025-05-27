package com.example.mynote.di

import android.content.Context
import com.example.mynote.dao.NoteDao
import com.example.mynote.networks.LoginApi
import com.example.mynote.networks.NoteApi
import com.example.mynote.repositories.LoginRepository
import com.example.mynote.repositories.NoteRepository
import com.example.mynote.repositories.SessionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule{
    @Provides
    @ViewModelScoped
    fun provideNoteRepository(
        api: NoteApi,
        dao: NoteDao
    ): NoteRepository {
        return NoteRepository(api, dao)
    }

    @Provides
    @ViewModelScoped
    fun provideSessionRepository(@ApplicationContext context: Context): SessionRepository {
        return SessionRepository(context)
    }

    @Provides
    @ViewModelScoped
    fun provideLoginRepository(api: LoginApi): LoginRepository {
        return LoginRepository(api)
    }

}