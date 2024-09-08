package com.gity.feliyafinance.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gity.feliyafinance.data.Repository
import com.gity.feliyafinance.data.local.model.User
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: Repository) : ViewModel() {
    private val _loginResult = MutableLiveData<User?>()
    val loginResult: LiveData<User?> get() = _loginResult

    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> get() = _registerResult

    private val _emailExists = MutableLiveData<Boolean>()
    val emailExists: LiveData<Boolean> = _emailExists

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = repository.loginUser(email, password)
            _loginResult.value = user
        }
    }

    fun register(user: User) {
        viewModelScope.launch {
            repository.registerUser(user)
            _registerResult.value = true
        }
    }

    fun checkEmailExists(email: String) {
        viewModelScope.launch {
            val user = repository.checkEmailExists(email)
            _emailExists.postValue(user != null) // True Jika Email Sudah Ada
        }
    }


}