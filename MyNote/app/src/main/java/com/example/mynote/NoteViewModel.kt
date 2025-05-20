package com.example.mynote


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
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {
    private val _loadList : MutableLiveData<Boolean> = MutableLiveData(false)
    val list : LiveData<List<Note>> = _loadList.switchMap {
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(
                noteRepository.loadItems(
                    onSuccess = {
                        Log.d("NoteViewModel", "load list success")
                    },
                    onError = {
                        Log.d("NoteViewModel", it)
                    }
                ).asLiveData()
            )
        }
    }

    fun insertNote(note: Note) {
        viewModelScope.launch {
            noteRepository.insert(note, onSuccess = {
                Log.d("NoteViewModel", "insert note success")
                }, onError = {
                Log.d("NoteViewModel", it)
            })
        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch {
            noteRepository.delete(note.id, onSuccess = {
                Log.d("NoteViewModel", "delete note success")
            }, onError = {
                Log.d("NoteViewModel", it)
            })
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteDao.updateNote(note)
        }
    }

}