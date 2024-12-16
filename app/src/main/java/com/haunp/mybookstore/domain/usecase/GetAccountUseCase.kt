package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.model.UserEntity
import com.haunp.mybookstore.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class GetAccountUseCase(private val userRepo: IUserRepository) {
    operator fun invoke(): Flow<List<UserEntity>> {
        return userRepo.getAllUser()
    }
}