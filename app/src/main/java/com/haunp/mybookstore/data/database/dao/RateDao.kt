package com.haunp.mybookstore.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.haunp.mybookstore.domain.model.RateEntity

@Dao
interface RateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(rateBook: RateEntity)

    @Update
    suspend fun updateComment(rateBook: RateEntity)

    @Delete()
    suspend fun deleteComment(rateBook: RateEntity)

    @Query("SELECT * FROM comments WHERE bookId = :productId")
    suspend fun getCommentsByProductId(productId: Int): List<RateEntity>
}