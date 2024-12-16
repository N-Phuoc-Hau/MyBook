package com.haunp.mybookstore.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carts")
data class CartEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    var bookId: String
)
