package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.model.UserEntity
import com.haunp.mybookstore.domain.repository.IUserRepository

class RegisterUseCase(private val userRepository: IUserRepository) {
    suspend operator fun invoke(userEntity: UserEntity): Long {
        return userRepository.registerUser(userEntity)
    }
}