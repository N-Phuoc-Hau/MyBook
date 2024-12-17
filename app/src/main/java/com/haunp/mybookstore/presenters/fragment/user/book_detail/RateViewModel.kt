package com.haunp.mybookstore.presenters.fragment.user.book_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haunp.mybookstore.domain.model.RateEntity
import com.haunp.mybookstore.domain.usecase.AddRateUseCase
import com.haunp.mybookstore.domain.usecase.GetListRateUseCase

class RateViewModel(private val addRateUseCase: AddRateUseCase,
                    private val getListRateUseCase: GetListRateUseCase) : ViewModel() {
     private val _rate = MutableLiveData<List<RateEntity>>()
     val rate: MutableLiveData<List<RateEntity>> get() = _rate


     suspend fun addRate(rateBook: RateEntity) {
          addRateUseCase.invoke(rateBook)
          val listRate = getListRateUseCase.invoke(rateBook.bookId)
          _rate.value = listRate
     }
     suspend fun getListRate(productId: Int){
          val listRate = getListRateUseCase.invoke(productId)
          _rate.value = listRate
     }
}