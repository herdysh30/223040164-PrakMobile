package com.example.mynote.ui.screens


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.mynote.models.Note
import com.example.mynote.repositories.NoteRepository
import com.example.mynote.repositories.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository,
    private val sessionRepository: SessionRepository) : ViewModel() {
        private val _token: MutableLiveData<String> = MutableLiveData("")

    val list: LiveData<List<Note>> = _token.switchMap { token ->
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(
                noteRepository.loadItems(token, onSuccess = {
                    Log.d("NoteViewModel", "load list success")
                }, onError = {
                    Log.d("NoteViewModel", it)
                }
                ).asLiveData()
            )
        }
    }

    fun updateToken(){
        viewModelScope.launch {
            sessionRepository.token.catch{
                Log.e("NoteViewModel", "Error Collection Token")
            }.collect{
                Log.d("NoteViewModel", "Token: $it")
                _token.postValue(it)
            }
        }
    }

    fun insertNote(note: Note) {
        viewModelScope.launch {
            sessionRepository.token.catch {
                Log.e("NoteViewModel", "Error Collection Token")
            }.collect{ token ->
                noteRepository.insert(token, note, onSuccess = {
                    Log.d("NoteViewModel", "insert note success")
                }, onError = {
                    Log.d("NoteViewModel", it)
                })
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            sessionRepository.token.catch {
                Log.e("NoteViewModel", "Error Collection Token")
            }.collect { token ->
                noteRepository.delete(token, note.id, onSuccess = {
                    Log.d("NoteViewModel", "delete note success")
                }, onError = {
                    Log.d("NoteViewModel", it)
                })
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            sessionRepository.token.catch {
                Log.e("NoteViewModel", "Error Collection Token")
            }.collect { token ->
                viewModelScope.launch {
                    noteRepository.update(token, note.id, note, onSuccess = {
                        Log.d("NoteViewModel", "update note success")
                    }, onError = {
                        Log.d("NoteViewModel", it)
                    })
                }
            }
        }
    }
}
