package com.haunp.mybookstore.presenters.fragment.main

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.haunp.mybookstore.R
import com.haunp.mybookstore.presenters.BookStoreManager
import com.haunp.mybookstore.presenters.CoreViewModel
import com.haunp.mybookstore.presenters.fragment.admin.book.BookFragment
import com.haunp.mybookstore.presenters.fragment.admin.category_admin.CategoryAdminFragment
import com.haunp.mybookstore.presenters.fragment.admin.statistical.StatisticalFragment
import com.haunp.mybookstore.presenters.fragment.admin.user.UserFragment
import com.haunp.mybookstore.presenters.fragment.user.book_detail.BookDetailFragment
import com.haunp.mybookstore.presenters.fragment.user.cart.CartFragment
import com.haunp.mybookstore.presenters.fragment.user.category_user.CategoryUserFragment
import com.haunp.mybookstore.presenters.fragment.user.home.HomeFragment
import com.haunp.mybookstore.presenters.fragment.user.search.SearchFragment
import com.haunp.mybookstore.presenters.fragment.user.setting.SettingFragment
import vn.zalopay.sdk.ZaloPaySDK

class MainActivity : AppCompatActivity() {
    private val coreViewModel: CoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            showFragment(HomeFragment())
            coreViewModel.logout()
        }
        coreViewModel.user.observe(this) {
            setBottomNavigation(it?.role ?: 2)
        }
        supportFragmentManager.addOnBackStackChangedListener {
            toggleBottomNavigationView()
        }
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_bottom_view)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    showFragment(HomeFragment())
                    true
                }

                R.id.nav_category -> {
                    showFragment(CategoryUserFragment())
                    true
                }

                R.id.nav_search -> {
                    showFragment(SearchFragment())
                    true
                }

                R.id.nav_setting -> {
                    showFragment(SettingFragment())
                    true
                }

                else -> false
            }
        }

        val bottomNavigationViewAdmin =
            findViewById<BottomNavigationView>(R.id.bottom_nav_menu_admin)
        bottomNavigationViewAdmin.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_book -> {
                    showFragment(BookFragment())
                    true
                }

                R.id.nav_category -> {
                    showFragment(CategoryAdminFragment())
                    true
                }

                R.id.nav_user -> {
                    showFragment(UserFragment())
                    true
                }

                R.id.nav_statistical -> {
                    showFragment(StatisticalFragment())
                    true
                }

                else -> false
            }
        }
        logOut()
        showCartFragment()
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun logOut() {
        val logout = findViewById<View>(R.id.fbn_logOut)
        logout.setOnClickListener {
            coreViewModel.logout()
        }
    }

    private fun showCartFragment() {
        val cart = findViewById<View>(R.id.fbn_cart)
        cart.setOnClickListener {
            showFragment(CartFragment())
        }
    }

    private fun setBottomNavigation(role: Int) {
        val bottomNavigationViewUser = findViewById<BottomNavigationView>(R.id.nav_bottom_view)
        val ftbCart = findViewById<View>(R.id.fbn_cart)
        val ftbLogOut = findViewById<View>(R.id.fbn_logOut)
        val bottomNavigationViewAdmin =
            findViewById<BottomNavigationView>(R.id.bottom_nav_menu_admin)
        Log.d("hau.np", "setBottomNavigation: $role")
        if (role == 0) {
            bottomNavigationViewUser.visibility = View.GONE
            bottomNavigationViewAdmin.visibility = View.VISIBLE
            ftbCart.visibility = View.GONE
            ftbLogOut.visibility = View.VISIBLE
            showFragment(BookFragment())
        }
        else if (role == 1) {
            bottomNavigationViewAdmin.visibility = View.GONE
            bottomNavigationViewUser.visibility = View.VISIBLE
            ftbLogOut.visibility = View.GONE
            ftbCart.visibility = View.VISIBLE
            showFragment(HomeFragment())
        }
        else{
            bottomNavigationViewAdmin.visibility = View.GONE
            bottomNavigationViewUser.visibility = View.VISIBLE
            ftbLogOut.visibility = View.GONE
            ftbCart.visibility = View.GONE
            showFragment(HomeFragment())
        }
    }
    private fun setBottomNavigationForOnCreate(){
        val bottomNavigationViewUser = findViewById<BottomNavigationView>(R.id.nav_bottom_view)
        val ftbCart = findViewById<View>(R.id.fbn_cart)
        val ftbLogOut = findViewById<View>(R.id.fbn_logOut)
        val bottomNavigationViewAdmin =
            findViewById<BottomNavigationView>(R.id.bottom_nav_menu_admin)
        if(BookStoreManager.idUser == null) {
            bottomNavigationViewAdmin.visibility = View.GONE
            bottomNavigationViewUser.visibility = View.VISIBLE
            ftbLogOut.visibility = View.GONE
            ftbCart.visibility = View.GONE
            showFragment(HomeFragment())
        }
    }
    private fun toggleBottomNavigationView() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_bottom_view)
        val floatButtonCart = findViewById<FloatingActionButton>(R.id.fbn_cart)
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        // Kiểm tra nếu là BookDetailFragment thì ẩn BottomNavigationView
        if (currentFragment is BookDetailFragment || currentFragment is CartFragment) {
            bottomNavigationView.visibility = View.GONE
            floatButtonCart.visibility = View.GONE
        }
        else {
            bottomNavigationView.visibility = View.VISIBLE
            if(BookStoreManager.idUser != null)
                floatButtonCart.visibility = View.VISIBLE
        }
    }

}