package com.haunp.mybookstore.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "books",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class BookEntity(
    @PrimaryKey(autoGenerate = true) val bookId: Int = 0,
    val title: String,
    val author: String,
    val price: Double,
    val description: String,
    val quantity: Int,
    val categoryId: Int, // Khóa ngoại đến bảng Categories
    val imageUri: String
):Parcelable