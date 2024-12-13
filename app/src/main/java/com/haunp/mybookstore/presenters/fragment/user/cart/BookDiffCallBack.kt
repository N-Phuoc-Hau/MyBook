package com.haunp.mybookstore.presenters.fragment.user.cart

import androidx.recyclerview.widget.DiffUtil
import com.haunp.mybookstore.domain.entity.BookEntity

class BookDiffCallback : DiffUtil.ItemCallback<BookEntity>() {
    override fun areItemsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean {
        // So sánh bookId (mã ID của sách), là giá trị duy nhất để xác định các đối tượng
        return oldItem.bookId == newItem.bookId
    }

    override fun areContentsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean {
        // So sánh tất cả các trường của BookEntity để kiểm tra nội dung có thay đổi không
        return oldItem == newItem
    }
}