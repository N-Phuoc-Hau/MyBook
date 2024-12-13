package com.haunp.mybookstore.presenters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haunp.mybookstore.domain.entity.UserEntity

class CoreViewModel(): ViewModel() {
// tạo biến livedate ở đây xong ở fragment nào cũng gọi được
//    private val _someLiveData = MutableLiveData<String>()
//    val someLiveData: LiveData<String> get() = _someLiveData
    private val _user = MutableLiveData<UserEntity?>()
    val user: MutableLiveData<UserEntity?> get() = _user

    fun setUser(user: UserEntity){
        _user.value = user
        BookStoreManager.idUser = user.userId
    }
    fun logout(){
        _user.value = null
        BookStoreManager.idUser = null
    }

}