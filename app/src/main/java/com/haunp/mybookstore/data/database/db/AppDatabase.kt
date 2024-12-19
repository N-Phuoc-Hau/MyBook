package com.haunp.mybookstore.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.haunp.mybookstore.data.database.dao.BookDao
import com.haunp.mybookstore.data.database.dao.CartDao
import com.haunp.mybookstore.data.database.dao.CategoryDao
import com.haunp.mybookstore.data.database.dao.RateDao
import com.haunp.mybookstore.data.database.dao.OrderDao
import com.haunp.mybookstore.data.database.dao.OrderDetailDao
import com.haunp.mybookstore.data.database.dao.UserDao
import com.haunp.mybookstore.data.database.dao.ZaloPayDao
import com.haunp.mybookstore.data.repository.CreateOrder
import com.haunp.mybookstore.domain.model.BookEntity
import com.haunp.mybookstore.domain.model.CartEntity
import com.haunp.mybookstore.domain.model.CategoryEntity
import com.haunp.mybookstore.domain.model.Converters
import com.haunp.mybookstore.domain.model.OrderEntity
import com.haunp.mybookstore.domain.model.OrderDetailEntity
import com.haunp.mybookstore.domain.model.RateEntity
import com.haunp.mybookstore.domain.model.UserEntity
import com.haunp.mybookstore.domain.model.ZaloPayOrder


@Database(
    entities = [UserEntity::class, BookEntity::class, CategoryEntity::class,
        CartEntity::class, OrderEntity::class, OrderDetailEntity::class, RateEntity::class, ZaloPayOrder::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getBookDao(): BookDao
    abstract fun getCategoriesDao(): CategoryDao
    abstract fun getCartDao(): CartDao
    abstract fun getOrderDao(): OrderDao
    abstract fun getRateDao(): RateDao
    abstract fun getOrderDetailDao(): OrderDetailDao
    abstract fun getZaloPayDao(): ZaloPayDao
}
