package com.haunp.mybookstore.presenters.fragment.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.haunp.mybookstore.R
import com.haunp.mybookstore.presenters.fragment.admin.book.BookFragment
import com.haunp.mybookstore.presenters.fragment.admin.category_admin.CategoryAdminFragment
import com.haunp.mybookstore.presenters.fragment.user.category_user.CategoryUserFragment
import com.haunp.mybookstore.presenters.fragment.user.home.HomeFragment
import com.haunp.mybookstore.presenters.fragment.user.search.SearchFragment
import com.haunp.mybookstore.presenters.fragment.user.setting.SettingFragment
import com.haunp.mybookstore.presenters.fragment.user.setting.SettingViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var settingViewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        settingViewModel = ViewModelProvider(this)[SettingViewModel::class.java]
        if (savedInstanceState == null) {
            showFragment(HomeFragment())
        }

        settingViewModel.user.observe(this) {
            Log.e("longtq", "onCreate: $it", )
            setBottomNavigation(0)
        }
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_bottom_view)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
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
                else->false
            }
        }

        val bottomNavigationViewAdmin =
            findViewById<BottomNavigationView>(R.id.bottom_nav_menu_admin)
        bottomNavigationViewAdmin.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_book -> {
                    showFragment(HomeFragment())
                    true
                }

                R.id.nav_category -> {
                    showFragment(CategoryAdminFragment())
                    true
                }

                R.id.nav_user -> {
                    showFragment(SearchFragment())
                    true
                }

                R.id.nav_statistical -> {
                    showFragment(SettingFragment())
                    true
                }

                else -> false
            }
        }
    }
    fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null) // Thêm vào back stack để có thể quay lại fragment trước
            .commit()
    }

    private fun setBottomNavigation(role: Int) {
        val bottomNavigationViewUser = findViewById<BottomNavigationView>(R.id.nav_bottom_view)
        val bottomNavigationViewAdmin =
            findViewById<BottomNavigationView>(R.id.bottom_nav_menu_admin)

        if (role == 0) {
            bottomNavigationViewUser.visibility = View.GONE
            bottomNavigationViewAdmin.visibility = View.VISIBLE
            showFragment(BookFragment())
        } else if (role == 1) {
            bottomNavigationViewAdmin.visibility = View.GONE
            bottomNavigationViewUser.visibility = View.VISIBLE
            showFragment(HomeFragment())
        }
    }
}