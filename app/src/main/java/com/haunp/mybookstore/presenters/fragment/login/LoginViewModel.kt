package com.haunp.mybookstore.presenters.fragment.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haunp.mybookstore.domain.model.UserEntity
import com.haunp.mybookstore.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _loginResult = MutableLiveData<UserEntity?>()
    val loginResult: LiveData<UserEntity?> get() = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val user = loginUseCase.invoke(username, password)
            Log.d("hau.np", "User: $user")
            _loginResult.value = user
        }
    }
}