package com.haunp.mybookstore.presenters.fragment.user.cart

import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.haunp.mybookstore.R
import com.haunp.mybookstore.databinding.CartFragmentBinding
import com.haunp.mybookstore.domain.model.OrderDetailEntity
import com.haunp.mybookstore.domain.model.OrderEntity
import com.haunp.mybookstore.presenters.BookStoreManager
import com.haunp.mybookstore.presenters.base.BaseFragment
import com.haunp.mybookstore.presenters.fragment.main.MainActivity
import com.haunp.mybookstore.presenters.fragment.user.home.HomeFragment
import com.haunp.mybookstore.presenters.fragment.user.setting.SettingFragment
import com.haunp.mybookstore.presenters.fragment.user.setting.SettingViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener
import java.text.NumberFormat
import java.time.LocalDate
import java.util.Locale
import java.util.logging.Handler


class CartFragment : BaseFragment<CartFragmentBinding>() {
    private val viewModel: CartViewModel by inject()
    private val orderViewModel: SettingViewModel by inject()
    private val paymentViewModel: PaymentViewModel by inject()
    private var totalAmount: String = ""
    override var isTerminalBackKeyActive: Boolean = true
    override fun getDataBinding(): CartFragmentBinding {
        return CartFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRetainInstance(true)
    }
    override fun initView() {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
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
            icCancel.setOnClickListener{
                (activity as MainActivity).showFragment(HomeFragment())
            }
            paymentViewModel.paymentResultLiveData.observe(viewLifecycleOwner){
                Log.d("hau.np","paymentResultLiveData: $it")
                if(it == "Thanh toán thành công"){
                    taoOrder()
                    Toast.makeText(context, "Thanh toán thành công", Toast.LENGTH_SHORT).show()
                }
                else if(it == "Hủy thanh toán"){
                    Toast.makeText(context, "Thanh toán thất bại", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

    override fun initAction() {
        binding {
            btnThanhToan.setOnClickListener {
//                thanhToan()
                taoOrder()

            }
        }
    }


    private fun thanhToan() {
        ZaloPaySDK.init(2553, Environment.SANDBOX)
        val totalString = "10000"
        try {
            lifecycleScope.launch {
                val data = paymentViewModel.createOrder(totalString)
                Log.d("hau.np","data: $data")
                val code = data.getString("return_code")
                Log.d("hau.np","return_code: $code")
                if (code == "1") {
                    val token = data.getString("zp_trans_token")
                    Log.d("hau.np","zp_trans_token: $token")
                    ZaloPaySDK.getInstance().payOrder(
                        requireActivity(),
                        token,
                        "demozpdk://app",
                        object : PayOrderListener {
                            override fun onPaymentSucceeded(
                                payUrl: String?,
                                transToken: String?,
                                appTransID: String?
                            ) {
                                paymentViewModel.setPaymentResult("Thanh toán thành công")
                                taoOrder()
                                Log.d("hau.np","Thanh toan thanh cong")
                            }

                            override fun onPaymentCanceled(payUrl: String?, transToken: String?) {
                                paymentViewModel.setPaymentResult("Hủy thanh toán")
                                Log.d("hau.np", "Payment canceled")
                            }

                            override fun onPaymentError(
                                error: ZaloPayError?,
                                payUrl: String?,
                                transToken: String?
                            ) {
                                paymentViewModel.setPaymentResult("Thanh toán thất bại")
                                Log.e("hau.np", "Payment error: $error")
                            }
                        })

                }

            }
        }catch (e: Exception)
        {
            e.printStackTrace()
            Log.e("ZaloPayError", "Exception: ${e.message}")
        }
    }
    private fun taoOrder(){
        lifecycleScope.launch {
            val userId = BookStoreManager.idUser
            val books = viewModel.bookInCart.value
            if(books!!.isEmpty()){
                Toast.makeText(context, "Giỏ hàng trống", Toast.LENGTH_SHORT).show()
                return@launch
            }
            val totalAmount = books.sumOf { it.price }
            val order = OrderEntity(userId = userId!!, orderDate = LocalDate.now().toString(), quantity = 1, totalAmount = totalAmount)
            val orderId = orderViewModel.insertOrder(order)
            orderViewModel.getOrder(userId)

            val orderDetails: List<OrderDetailEntity> = books.map { book ->
                OrderDetailEntity(
                    orderId = orderId.toInt(), // ID của đơn hàng
                    bookId = book.bookId,      // ID của sách
                    quantity = 1,             // Số lượng mặc định là 1
                    price = book.price        // Giá sách
                )
            }.toList()
            Log.d("hau.np", orderDetails.toString())
            orderViewModel.insertOrderDetails(orderDetails)
            Toast.makeText(context, "Đặt hàng thành công", Toast.LENGTH_SHORT).show()

            viewModel.clearCart(userId)
            (activity as MainActivity).showFragment(SettingFragment())
        }
    }
}
