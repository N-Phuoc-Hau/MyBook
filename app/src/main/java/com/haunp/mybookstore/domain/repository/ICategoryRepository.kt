package com.haunp.mybookstore.domain.repository

import androidx.lifecycle.LiveData
import com.haunp.mybookstore.domain.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface ICategoryRepository {
    fun getAllCategory(): Flow<List<CategoryEntity>>
    suspend fun addCategory(categoryEntity: CategoryEntity)
    suspend fun deleteCategory(id: Int)
    suspend fun updateCategory(categoryEntity: CategoryEntity)
}