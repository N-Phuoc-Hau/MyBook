package com.haunp.mybookstore.presenters.fragment.admin.category_admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.haunp.mybookstore.domain.model.CategoryEntity
import com.haunp.mybookstore.domain.usecase.AddCateUseCase
import com.haunp.mybookstore.domain.usecase.DelCateUseCase
import com.haunp.mybookstore.domain.usecase.GetCateUseCase
import com.haunp.mybookstore.domain.usecase.UpdateCateUseCase

import kotlinx.coroutines.launch

class CategoryAdminViewModel(private val getCateUseCase : GetCateUseCase,
                             private val addCateUseCase : AddCateUseCase,
                             private val deleteCateUseCase : DelCateUseCase,
                            private val updateCateUseCase : UpdateCateUseCase
) : ViewModel() {

    val categories : LiveData<List<CategoryEntity>> = liveData {
        emitSource(getCateUseCase().asLiveData())
    }

    fun addCategory(categoryEntity: CategoryEntity){
        viewModelScope.launch {
            addCateUseCase.invoke(categoryEntity)
        }
    }
    fun deleteCategory(categoryId: Int) {
        viewModelScope.launch {
            deleteCateUseCase.invoke(categoryId)
        }
    }
    fun updateCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            updateCateUseCase.invoke(categoryEntity)
        }

    }
}