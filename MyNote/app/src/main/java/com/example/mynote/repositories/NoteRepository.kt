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
        token: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = flow {
        val list: List<Note> = dao.getAllNotes()
        if (token.isEmpty()){
            onError("Token is Empty")
            emit(list)
        }
        api.all("Bearer $token")
            .suspendOnSuccess {
                data.whatIfNotNull { response ->
                    dao.insertNotes(data.data!!)
                    val localList: List<Note> = dao.getAllNotes()
                    emit(localList)
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


    suspend fun insert(token:String, item: Note, onSuccess: () -> Unit, onError: (String) -> Unit){
        api.insert("Bearer $token", item)
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

    suspend fun update(token: String,id: String, item: Note, onSuccess: () -> Unit, onError: (String) -> Unit){
        api.update("Bearer $token",id, item)
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

    suspend fun delete(token: String, id: String, onSuccess: () -> Unit, onError: (String) -> Unit){
        api.delete("Bearer $token",id)
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