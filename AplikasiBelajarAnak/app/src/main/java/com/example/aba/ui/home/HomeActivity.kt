package com.example.aba.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aba.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
    }
}