package com.example.aba.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.aba.R
import com.example.aba.databinding.ActivityHomeBinding
import com.example.aba.ui.pencapaian.PencapaianFragment
import com.example.aba.ui.profile.ProfileFragment
import com.example.aba.ui.settings.SettingFragment
import com.google.android.material.navigation.NavigationBarView


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val bottomNav = binding.bottomNavigation

        val fragment = HomeFragment.newInstance("test1","test2")
        bottomNav.setOnItemSelectedListener(menuItemSelected)
        addFragment(fragment)
    }

    private val menuItemSelected = NavigationBarView.OnItemSelectedListener  { item ->
        when (item.itemId) {
            R.id.menu_home ->{
                val fragment = HomeFragment.newInstance("test1","test2")
                addFragment(fragment)
                return@OnItemSelectedListener  true
            }
            R.id.menu_progress ->{
                val fragment = PencapaianFragment.newInstance("test1","test2")
                addFragment(fragment)
                return@OnItemSelectedListener true
            }
            R.id.menu_profile ->{
                val fragment = ProfileFragment.newInstance("test1","test2")
                addFragment(fragment)
                return@OnItemSelectedListener true
            }
            R.id.menu_settings ->{
                val fragment = SettingFragment.newInstance("test1","test2")
                addFragment(fragment)
                return@OnItemSelectedListener true
            }
        }
        false
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_baru, fragment, fragment.javaClass.getSimpleName())
            .commit()
    }

}