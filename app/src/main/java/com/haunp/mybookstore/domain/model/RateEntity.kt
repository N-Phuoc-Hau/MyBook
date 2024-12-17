package com.haunp.mybookstore.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "comments",
    foreignKeys = [
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = ["bookId"],
            childColumns = ["bookId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RateEntity(
    @PrimaryKey(autoGenerate = true) val rateBookId: Int = 0,
    val userName: String,
    val bookId: Int,
    val rating: Int,
    val comment: String
)