package com.haunp.mybookstore.presenters.fragment.user.cart

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.haunp.mybookstore.R
import com.haunp.mybookstore.data.api.CreateOrder
import com.haunp.mybookstore.databinding.CartFragmentBinding
import com.haunp.mybookstore.domain.constant.AppInfo.APP_ID
import com.haunp.mybookstore.domain.model.BookEntity
import com.haunp.mybookstore.domain.model.OrderDetailEntity
import com.haunp.mybookstore.domain.model.OrderEntity
import com.haunp.mybookstore.domain.repository.OnQuantityChangedListener
import com.haunp.mybookstore.presenters.BookStoreManager
import com.haunp.mybookstore.presenters.base.BaseFragment
import com.haunp.mybookstore.presenters.fragment.main.MainActivity
import com.haunp.mybookstore.presenters.fragment.user.home.HomeAdapter
import com.haunp.mybookstore.presenters.fragment.user.setting.SettingFragment
import com.haunp.mybookstore.presenters.fragment.user.setting.SettingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener
import java.text.NumberFormat
import java.time.LocalDate
import java.util.Locale


class CartFragment : BaseFragment<CartFragmentBinding>(){
    private var paymentResult = MutableLiveData<String>("Đang đợi thanh toán")
    private val totalString = MutableLiveData<String>("10000")
    private val viewModel: CartViewModel by inject()
    private val orderViewModel : SettingViewModel by inject()
    override var isTerminalBackKeyActive: Boolean = true
    override fun getDataBinding(): CartFragmentBinding {
        return CartFragmentBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding {
            val adapter = CartAdapter(viewModel)
            rVCart.adapter = adapter
            rVCart.layoutManager = LinearLayoutManager(context)
            viewModel.getBookInCart(BookStoreManager.idUser!!)
            viewModel.bookInCart.observe(viewLifecycleOwner) { books ->
                adapter.submitList(books)
                val numberFormat = NumberFormat.getNumberInstance(Locale("vi", "VN"))
                val formattedPrice = numberFormat.format(books.sumOf { it.price })
                tvPrice.text = "$formattedPrice đ"
                tvPrice.setTextColor(ContextCompat.getColor(requireContext(), R.color.red_100))
                tvPrice.textSize = 18f
                tvPrice.setTypeface(null, android.graphics.Typeface.BOLD)
            }
        }

    }

    override fun initAction() {
        binding {
            btnThanhToan.setOnClickListener {
                checkout()
            }
        }
    }
    private fun checkout() {
        ZaloPaySDK.init(APP_ID, Environment.SANDBOX)
        thanhToan()
    }

    private fun thanhToan() {
        val orderApi = CreateOrder()

        lifecycleScope.launch {
            try {
                if (totalString.value.isNullOrEmpty()) {
                    Toast.makeText(context, "Tổng thanh toán không hợp lệ", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val data = withContext(Dispatchers.IO) { orderApi.createOrder(totalString.toString()) }
                Log.d("ZaloPay", "Order API response: $data")

                val code = data.getString("return_code")
                if (code == "1") {
                    val token = data.getString("zp_trans_token")
                    ZaloPaySDK.getInstance().payOrder(
                        requireActivity(),
                        token,
                        "demozpdk://app",
                        object : PayOrderListener {
                            override fun onPaymentSucceeded(payUrl: String?, transToken: String?, appTransID: String?) {
                                paymentResult.value = "Thanh toán thành công"
                                Log.d("ZaloPay", "Payment succeeded")
                            }

                            override fun onPaymentCanceled(payUrl: String?, transToken: String?) {
                                paymentResult.value = "Hủy thanh toán"
                                Log.d("ZaloPay", "Payment canceled")
                            }

                            override fun onPaymentError(error: ZaloPayError?, payUrl: String?, transToken: String?) {
                                paymentResult.value = "Lỗi thanh toán"
                                Log.e("ZaloPay", "Payment error: $error")
                            }
                        }
                    )
                    paymentResult.value = "Đang chờ thanh toán..."
                } else {
                    Toast.makeText(context, "Không thể tạo đơn hàng", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                paymentResult.value = "Lỗi: ${e.message}"
                Log.e("ZaloPay", "Exception: ${e.message}")
            }
        }
    }

}
