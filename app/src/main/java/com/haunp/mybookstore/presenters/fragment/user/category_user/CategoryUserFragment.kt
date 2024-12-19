package com.haunp.mybookstore.presenters.fragment.user.category_user

import android.content.Context
import android.util.Log
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.haunp.mybookstore.R.id.fragment_container
import com.haunp.mybookstore.databinding.CategoryUserFragmentBinding
import com.haunp.mybookstore.domain.model.CategoryEntity
import com.haunp.mybookstore.presenters.base.BaseFragment
import com.haunp.mybookstore.presenters.fragment.user.book_detail.CategoryDetailFragment
import org.koin.android.ext.android.inject

class CategoryUserFragment : BaseFragment<CategoryUserFragmentBinding>() {
    private val viewModel: CategoryUserViewModel by inject()
    override var isTerminalBackKeyActive: Boolean = true

    override fun getDataBinding(): CategoryUserFragmentBinding {
        return CategoryUserFragmentBinding.inflate(layoutInflater)
    }

    var adapter = CategoryUserAdapter()
    override fun initView() {
        binding.categoryRecyclerView.adapter = adapter
        binding.categoryRecyclerView.layoutManager = GridLayoutManager(context,2)
        // Lắng nghe LiveData và cập nhật RecyclerView
        val categoryList = getCategoriesFromSharedPreferences()
        adapter.submitList(categoryList)
    }

    override fun initAction() {
        val categoryDetailFragment = CategoryDetailFragment()
        adapter.onItemClick = { category ->
            val bundle = Bundle().apply{
                putInt("categoryId",category.categoryId)
                putString("categoryName",category.name)
                Log.d("hau,np", "initAction: ${category.categoryId}")
            }
            categoryDetailFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(fragment_container, categoryDetailFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun getCategoriesFromSharedPreferences(): List<CategoryEntity> {
        val sharedPreferences = requireContext().getSharedPreferences("CateAppPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("cate_list", null)

        // Nếu không có dữ liệu, trả về danh sách rỗng
        return if (json != null) {
            val type = object : TypeToken<List<CategoryEntity>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }
}