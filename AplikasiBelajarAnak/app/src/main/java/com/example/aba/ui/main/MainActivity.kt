package com.example.aba.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aba.databinding.ActivityMainBinding
import com.example.aba.ui.login.LoginActivity
import com.example.aba.ui.register.RegisterActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btRegis.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}