package com.example.aba.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aba.R
import com.example.aba.databinding.ActivityHomeBinding
import com.example.aba.databinding.ActivityLoginBinding
import com.example.aba.ui.settings.SettingsActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val bottomNav = binding.bottomNavigation

        bottomNav.setOnItemSelectedListener{
            when(it.itemId){
                R.id.menu_home -> startActivity(Intent(this,HomeActivity::class.java))
                R.id.menu_settings -> startActivity(Intent(this,SettingsActivity::class.java))
            }
            true
        }
    }
}