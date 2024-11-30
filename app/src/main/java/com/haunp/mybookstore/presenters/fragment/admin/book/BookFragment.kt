package com.haunp.mybookstore.presenters.fragment.admin.book

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.haunp.mybookstore.databinding.BookFragmentBinding
import com.haunp.mybookstore.domain.entity.BookEntity
import com.haunp.mybookstore.presenters.base.BaseFragment
import org.koin.android.ext.android.inject
import java.util.Locale

class BookFragment : BaseFragment<BookFragmentBinding>() {
    private var selectedImageUri: Uri? = null
    private val viewModel: BookViewModel by inject()


    override var isTerminalBackKeyActive: Boolean = true

    override fun getDataBinding(): BookFragmentBinding {
        return BookFragmentBinding.inflate(layoutInflater)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    override fun initView() {
        val adapter = BookAdapter()
        binding.bookAdminRecyclerView.adapter = adapter
        binding.bookAdminRecyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.books.observe(viewLifecycleOwner) { bookList ->
            adapter.submitList(bookList)
        }
        binding{
            btnSelectBook.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
                imagePickerLauncher.launch(intent)
            }
            btnAdd.setOnClickListener {
                val title = edtTitle.text.toString()
                val author = edtAuthor.text.toString()
                val price = edtPrice.text.toString()
                val quantity = edtQuantity.text.toString()
                val category = edtCategory.text.toString()
                val imageUriString = selectedImageUri?.toString() ?: ""
                val bookEntity = BookEntity(
                    title = title,
                    author = author,
                    price = price.toDouble(),
                    quantity = quantity.toInt(),
                    categoryId = 1,
                    imageUri = imageUriString
                )
                viewModel.addBook(bookEntity)
                edtIdBook.setText("")
                edtTitle.setText("")
                edtAuthor.setText("")
                edtPrice.setText("")
                edtQuantity.setText("")
                edtCategory.setText("")
            }
//            btnDel.setOnClickListener {
//                val id = edtIdBook.text.toString()
//                viewModel.deleteBook(id.toInt())
//                edtIdBook.setText("")
//            }
            btnUpdate.setOnClickListener {
                val id = edtIdBook.text.toString()
                val title = edtTitle.text.toString()
                val author = edtAuthor.text.toString()
                val price = edtPrice.text.toString()
                val quantity = edtQuantity.text.toString()
                val category = edtCategory.text.toString()
                val imageUriString = selectedImageUri?.toString() ?: ""
                val bookEntity = BookEntity(
                    bookId = id.toInt(),
                    title = title,
                    author = author,
                    price = price.toDouble(),
                    quantity = quantity.toInt(),
                    categoryId = 0,
                    imageUri = imageUriString

                )
                viewModel.updateBook(bookEntity)
            }
        }
    }
    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedImageUri = result.data?.data
        }
    }


}