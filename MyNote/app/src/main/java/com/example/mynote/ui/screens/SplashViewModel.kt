package com.example.mynote.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynote.repositories.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(private val sessionRepository: SessionRepository) : ViewModel() {
    fun checkSession(onSuccess: () -> Unit, onError: () -> Unit){
        viewModelScope.launch {
            sessionRepository.token.catch{
                onError()
            }.collect{
                if (it.isNotEmpty()){
                    onSuccess()
                }else{
                    onError()
                }
            }
        }
    }
}