package com.haunp.mybookstore.domain.model

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun toBookIdList(bookIds: String): List<Int> {
        return bookIds.split(",").map { it.toInt() } // Chuyển String thành List<Int>
    }

    @TypeConverter
    fun fromBookIdList(bookIds: List<Int>): String {
        return bookIds.joinToString(",") // Chuyển List<Int> thành String
    }
}