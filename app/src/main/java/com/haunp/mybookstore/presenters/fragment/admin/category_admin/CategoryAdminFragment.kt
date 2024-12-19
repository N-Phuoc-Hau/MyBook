package com.haunp.mybookstore.presenters.fragment.admin.category_admin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.haunp.mybookstore.databinding.CategoryAdminFragmentBinding
import com.haunp.mybookstore.domain.model.CategoryEntity
import com.haunp.mybookstore.presenters.base.BaseFragment
import org.koin.android.ext.android.inject

class CategoryAdminFragment : BaseFragment<CategoryAdminFragmentBinding>() {
    private var selectedImageUri: Uri? = null
    private val viewModel: CategoryAdminViewModel by inject()
    override var isTerminalBackKeyActive: Boolean = true

    override fun getDataBinding(): CategoryAdminFragmentBinding {
        return CategoryAdminFragmentBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val adapter = CategoryAdapter(viewModel){
            binding.edtIDCate.setText(it.toString())
        }
        binding.categoryAdminRecyclerView.adapter = adapter
        binding.categoryAdminRecyclerView.layoutManager = GridLayoutManager(context, 2)

        // Lắng nghe LiveData và cập nhật RecyclerView
        viewModel.categories.observe(viewLifecycleOwner) { categoryList ->
            adapter.submitList(categoryList)
            saveCategoriesToSharedPreferences(categoryList)
        }
    }
    override fun initAction() {
        binding{
            btnSelectImage.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
                imagePickerLauncher.launch(intent)
            }
            btnAdd.setOnClickListener {
                val name = edtNameCate.text.toString().trim()
                val imageUriString = selectedImageUri?.toString() ?: ""
                if (name.isNotEmpty()) {
                    val categoryEntity = CategoryEntity(name = name, imageUri = imageUriString)
                    viewModel.addCategory(categoryEntity)
                    clearText()
                    Toast.makeText(context, "Thêm danh mục thành công!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Vui lòng nhập tên danh mục!", Toast.LENGTH_SHORT).show()
                }
            }
            btnUpdate.setOnClickListener {
                val id = edtIDCate.text.toString().trim()
                val name = edtNameCate.text.toString().trim()
                val imageUriString = selectedImageUri?.toString() ?: ""
                if (id.isBlank() || name.isBlank()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val categoryId = id.toInt()
                val categoryEntity = CategoryEntity(categoryId = categoryId, name = name, imageUri = imageUriString)
                val categories = viewModel.categories.value ?: emptyList()
                for (cate in categories) {
                    if (cate.categoryId == id.toInt()) {
                        viewModel.updateCategory(categoryEntity)
                        Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show()
                        clearText()
                        return@setOnClickListener
                    }
                }
                Toast.makeText(context, "Không tìm thấy sách với ID này", Toast.LENGTH_SHORT).show()
                clearText()
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

    private fun saveCategoriesToSharedPreferences(categoryList: List<CategoryEntity>) {
        val sharedPreferences = requireContext().getSharedPreferences("CateAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Chuyển danh sách thành JSON
        val gson = Gson()
        val json = gson.toJson(categoryList)

        // Lưu JSON vào SharedPreferences
        editor.putString("cate_list", json)
        editor.apply()
    }

    private fun clearText(){
        binding{
            edtIDCate.setText("")
            edtNameCate.setText("")
            selectedImageUri = null
        }

    }
}