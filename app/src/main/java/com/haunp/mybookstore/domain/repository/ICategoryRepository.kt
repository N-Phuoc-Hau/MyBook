package com.haunp.mybookstore.domain.repository

import com.haunp.mybookstore.domain.model.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface ICategoryRepository {
    fun getAllCategory(): Flow<List<CategoryEntity>>
    suspend fun addCategory(categoryEntity: CategoryEntity)
    suspend fun deleteCategory(id: Int)
    suspend fun updateCategory(categoryEntity: CategoryEntity)
    suspend fun getCategoryById(id: Int): CategoryEntity?
}