package com.haunp.mybookstore.presenters.fragment.user.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.haunp.mybookstore.R
import com.haunp.mybookstore.databinding.HomeFragmentBinding
import com.haunp.mybookstore.domain.model.BookEntity
import com.haunp.mybookstore.presenters.BookStoreManager
import com.haunp.mybookstore.presenters.base.BaseFragment
import com.haunp.mybookstore.presenters.fragment.login.LoginFragment
import com.haunp.mybookstore.presenters.fragment.main.MainActivity
import com.haunp.mybookstore.presenters.fragment.user.book_detail.BookDetailFragment


class HomeFragment : BaseFragment<HomeFragmentBinding>() {

    override var isTerminalBackKeyActive: Boolean = true

    // Danh sách hình ảnh cho banner
    private val imageList = listOf(
        R.drawable.quangcao1, // Thay bằng tên ảnh trong drawable
        R.drawable.quangcao2,
        R.drawable.quangcao3,
        R.drawable.quangcao4
    )
    private var currentIndex = 0
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var bannerImageView: ImageView

    private val slideshowRunnable = object : Runnable {
        override fun run() {
            bannerImageView.setImageResource(imageList[currentIndex])
            currentIndex = (currentIndex + 1) % imageList.size
            handler.postDelayed(this, 3000) // Thời gian chuyển ảnh (3 giây)
        }
    }

    // Khởi tạo

    override fun getDataBinding(): HomeFragmentBinding {
        return HomeFragmentBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setupObservers()
    }
    var adapter = HomeAdapter()
    override fun initView() {
        binding{
            homeRecyclerView.layoutManager = GridLayoutManager(context,2)
            homeRecyclerView.adapter = adapter
        }
        // Lấy dữ liệu từ SharedPreferences và cập nhật adapter
        val bookList = getBooksFromSharedPreferences()
        adapter.submitList(bookList)
        // Khởi động banner slideshow
        bannerImageView = binding.bannerImageView
        handler.post(slideshowRunnable)

    }

    override fun initAction() {
        adapter.onItemClick = { book ->
            if (BookStoreManager.idUser == null)
                (activity as MainActivity).showFragment(LoginFragment())
            else{
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

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(slideshowRunnable)
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
