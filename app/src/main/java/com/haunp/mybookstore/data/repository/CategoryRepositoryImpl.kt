package com.haunp.mybookstore.data.repository

import com.haunp.mybookstore.data.database.dao.CategoryDao
import com.haunp.mybookstore.domain.model.CategoryEntity
import com.haunp.mybookstore.domain.repository.ICategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(private var categoryDao: CategoryDao): ICategoryRepository {

    override fun getAllCategory(): Flow<List<CategoryEntity>> {
        return categoryDao.getAllCategory()
    }

    override suspend fun addCategory(categoryEntity: CategoryEntity) {
        return categoryDao.insertCategory(categoryEntity)
    }

    override suspend fun deleteCategory(id: Int) {
        return categoryDao.deleteCategory(id)
    }

    override suspend fun updateCategory(categoryEntity: CategoryEntity) {
        return categoryDao.updateCategory(categoryEntity)
    }

    override suspend fun getCategoryById(id: Int): CategoryEntity? {
        return categoryDao.getCategoryById(id)
    }

}