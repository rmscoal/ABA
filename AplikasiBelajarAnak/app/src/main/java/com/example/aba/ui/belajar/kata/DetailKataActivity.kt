package com.example.aba.ui.belajar.kata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.example.aba.R
import com.example.aba.databinding.ActivityDetailBelajarKataBinding

class DetailKataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBelajarKataBinding
    companion object{
        private var LEMA = "lema"
        private var NILAI = "nilai"
        private var URL = "url"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBelajarKataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.tvLema.text = intent.getStringExtra(LEMA)
        binding.tvDeskripsi.text = intent.getStringExtra(NILAI)
        if (intent.getStringExtra(URL)=="NA"){
            binding.ivImage.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.logo, null))
            binding.tvWarning.visibility = View.VISIBLE
        }
        else{
            Glide.with(this)
                .load(intent.getStringExtra(URL))
                .fitCenter()
                .into(binding.ivImage)
            binding.tvWarning.visibility = View.GONE
        }

        binding.btBack.setOnClickListener {
            startActivity(Intent(this,KataActivity::class.java))
        }
    }
}