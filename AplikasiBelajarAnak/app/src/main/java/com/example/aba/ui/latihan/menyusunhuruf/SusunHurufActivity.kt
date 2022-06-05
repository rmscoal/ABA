package com.example.aba.ui.latihan.menyusunhuruf

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aba.databinding.ActivitySusunHurufBinding

class SusunHurufActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySusunHurufBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySusunHurufBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.hurufA.setOnClickListener {
            startActivity(Intent(this, DetailSusunHurufActivity::class.java))
        }
    }
}