package com.haunp.mybookstore.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    val email: String = "",
    val username: String = "",
    val phone: String = "",
    val address: String = "",
    val password: String = "",
    val role: Int? = 1// 0 : Admin or 1: User
) : Parcelable