package com.example.mynote.repositories

import androidx.annotation.WorkerThread
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
            //handle the case when the API request gets a success response
            .suspendOnSuccess {
                data.whatIfNotNull {
                    dao.insertNotes(list)
                    emit(list)
                    onSuccess()
                }
            }
            // handle the case when the API request gets an error response
            // e.g. internal server error
            .suspendOnError {
                onError(message())
                emit(list)
            }
            //handle the case when the API request gets an exception response
            //e.g network connection error.
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
}