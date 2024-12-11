package com.haunp.mybookstore.presenters.fragment.user

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.haunp.mybookstore.R
import com.haunp.mybookstore.databinding.CategoryDetailFragmentBinding
import com.haunp.mybookstore.databinding.CategoryUserFragmentBinding
import com.haunp.mybookstore.domain.entity.BookEntity
import com.haunp.mybookstore.presenters.base.BaseFragment
import com.haunp.mybookstore.presenters.fragment.admin.book.BookAdapter
import com.haunp.mybookstore.presenters.fragment.user.category_user.CategoryUserViewModel
import com.haunp.mybookstore.presenters.fragment.user.home.HomeAdapter
import org.koin.android.ext.android.inject

class CategoryDetailFragment:BaseFragment<CategoryDetailFragmentBinding>() {
    private val viewModel: CategoryUserViewModel by inject()
    override var isTerminalBackKeyActive: Boolean = true

    override fun getDataBinding(): CategoryDetailFragmentBinding {
        return CategoryDetailFragmentBinding.inflate(layoutInflater)
    }
    var homeAdapter = HomeAdapter()
    override fun initView() {
        binding{
            recCategory.layoutManager = GridLayoutManager(context,2)
            recCategory.adapter = homeAdapter
        }
        val categoryId = arguments?.getInt("categoryId")?: 0
        viewModel.getBookByCateID(categoryId)
        viewModel.booksByCategory.observe(viewLifecycleOwner){
            homeAdapter.submitList(it)
        }
    }

    override fun initAction() {
        homeAdapter.onItemClick = { book ->
            val bookDetailFragment = BookDetailFragment()
            val bundle = Bundle().apply {
                putParcelable("book", book)
            }
            bookDetailFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, bookDetailFragment)
                .addToBackStack(null)
                .commit()
        }
    }

}