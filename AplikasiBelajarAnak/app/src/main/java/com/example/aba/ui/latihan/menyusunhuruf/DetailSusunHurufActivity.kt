package com.example.aba.ui.latihan.menyusunhuruf

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aba.R
import com.example.aba.databinding.ActivityDetailSusunHurufBinding
import com.example.aba.databinding.ActivityLoginBinding
import com.example.aba.databinding.ActivityRecordAudioBinding
import com.example.aba.databinding.ActivitySusunHurufBinding

class DetailSusunHurufActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSusunHurufBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSusunHurufBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.hurufA.setOnClickListener{
            binding.ivWadah.setImageResource(R.drawable.alfa_a)
        }
    }
}