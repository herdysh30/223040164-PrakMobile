package com.example.mynote.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.skydoves.sandwich.suspendOnSuccess
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.message
import com.skydoves.whatif.whatIfNotNull
import com.example.mynote.dao.NoteDao
import com.example.mynote.models.Note
import com.example.mynote.networks.NoteApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val api: NoteApi,
    private val dao: NoteDao
) {
    @WorkerThread
    fun loadItems(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = flow {
        val list: List<Note> = dao.getAllNotes()
        api.all()
            .suspendOnSuccess {
                data.whatIfNotNull { response ->
                    dao.insertNotes(response.data)
                    emit(dao.getAllNotes())
                    onSuccess()
                }
            }
            .suspendOnError {
                onError(message())
                emit(list)
            }
            .suspendOnException {
                onError(message())
                emit(list)
            }
    }.flowOn(Dispatchers.IO)


    suspend fun insert(item: Note, onSuccess: () -> Unit, onError: (String) -> Unit){
        api.insert(item)
            .suspendOnSuccess {
                dao.insertNote(item)
                onSuccess()
            }
            .suspendOnError {
                onError(message())
            }
            .suspendOnException {
                onError(message())
            }
    }

    suspend fun update(id: String, item: Note, onSuccess: () -> Unit, onError: (String) -> Unit){
        api.update(id, item)
            .suspendOnSuccess {
                dao.updateNote(item)
                onSuccess()
            }
            .suspendOnError {
                onError(message())
            }
            .suspendOnException {
                onError(message())
            }
    }

    suspend fun delete(id: String, onSuccess: () -> Unit, onError: (String) -> Unit){
        api.delete(id)
            .suspendOnSuccess {
                dao.deleteNote(id)
                onSuccess()
            }
            .suspendOnError {
                onError(message())
            }
            .suspendOnException {
                onError(message())
            }
    }

    fun getLiveNotes(): LiveData<List<Note>> = dao.getAllNotesLive()

    @WorkerThread
    suspend fun refreshNotes(onSuccess: () -> Unit, onError: (String) -> Unit) {
        api.all()
            .suspendOnSuccess {
                data.whatIfNotNull { response ->
                    dao.insertNotes(response.data)
                    onSuccess()
                }
            }
            .suspendOnError {
                onError(message())
            }
            .suspendOnException {
                onError(message())
            }
    }

}