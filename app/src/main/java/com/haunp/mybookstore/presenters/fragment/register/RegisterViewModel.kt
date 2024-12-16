package com.haunp.mybookstore.presenters.fragment.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haunp.mybookstore.domain.model.UserEntity
import com.haunp.mybookstore.domain.usecase.RegisterUseCase
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    fun registerUser(userEntity: UserEntity) {
        viewModelScope.launch {
            registerUseCase.invoke(userEntity)
        }
    }

}