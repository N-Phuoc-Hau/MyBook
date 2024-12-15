package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.repository.IUserRepository

class DelUserUseCase (private val userRepository: IUserRepository){
    suspend operator fun invoke(userId: Int) {
        userRepository.deleteUser(userId)
    }
}