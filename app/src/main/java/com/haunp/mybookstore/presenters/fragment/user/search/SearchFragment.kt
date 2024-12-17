package com.haunp.mybookstore.presenters.fragment.user.search

import android.content.Context
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.haunp.mybookstore.R
import com.haunp.mybookstore.databinding.SearchFragmentBinding
import com.haunp.mybookstore.domain.model.BookEntity
import com.haunp.mybookstore.presenters.base.BaseFragment
import com.haunp.mybookstore.presenters.fragment.user.book_detail.BookDetailFragment
import com.haunp.mybookstore.presenters.fragment.user.home.HomeAdapter
import org.koin.android.ext.android.inject

class SearchFragment: BaseFragment<SearchFragmentBinding>() {
    private val viewModel: SearchViewModel by inject()

    override var isTerminalBackKeyActive: Boolean
        = true

    override fun getDataBinding(): SearchFragmentBinding {
        return SearchFragmentBinding.inflate(layoutInflater)
    }

    var adapter = HomeAdapter()
    override fun initView() {
        binding.searchResultsRecyclerView.adapter = adapter
        binding.searchResultsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.edtSearch.addTextChangedListener { searchText ->
            val query = searchText.toString().lowercase()
            val bookList = getBooksFromSharedPreferences()
            val filteredBooks = bookList.filter { book ->
                book.title.lowercase().contains(query) ||
                        book.author.lowercase().contains(query)
            }
            adapter.submitList(filteredBooks)
        }
    }

    override fun initAction() {
        adapter.onItemClick = { book ->
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

    private fun getBooksFromSharedPreferences(): List<BookEntity> {
        val sharedPreferences = requireContext().getSharedPreferences("BookAppPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("book_list", null)

        // Nếu không có dữ liệu, trả về danh sách rỗng
        return if (json != null) {
            val type = object : TypeToken<List<BookEntity>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }
}