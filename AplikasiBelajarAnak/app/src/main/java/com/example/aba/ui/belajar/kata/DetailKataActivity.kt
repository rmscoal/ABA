package com.example.aba.ui.belajar.kata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.aba.databinding.ActivityDetailKataBinding

class DetailKataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailKataBinding
    companion object{
        private var LEMA = "lema"
        private var NILAI = "nilai"
        private var URL = "url"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailKataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.tvLema.text = intent.getStringExtra(LEMA)
        binding.tvDeskripsi.text = intent.getStringExtra(NILAI)
        if (URL!="NA"){
            Glide.with(this)
                .load(intent.getStringExtra(URL))
                .fitCenter()
                .into(binding.ivImage)
        }
    }
}