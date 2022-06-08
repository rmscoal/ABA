package com.example.aba.ui.latihan.mengejakata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.example.aba.R
import com.example.aba.databinding.ActivityHasilRecordMengejaKataBinding
import com.example.aba.ui.belajar.kata.KataActivity

class HasilRecordMengejaKataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHasilRecordMengejaKataBinding
    companion object{
        private const val RESULT = "RESULT"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHasilRecordMengejaKataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val hasil = intent.getBooleanExtra(RESULT,false)

        if (hasil){
            binding.tvHasil.text = resources.getString(R.string.hasil_benar)
            binding.ivHasil.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.img_benar, null))
        }
        else{
            binding.tvHasil.text = resources.getString(R.string.hasil_salah)
            binding.ivHasil.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.img_salah, null))
        }

        binding.btBack.setOnClickListener {
            startActivity(Intent(this, RecordMengejaKataActivity::class.java))
            finish()
        }
        binding.btBackTop.setOnClickListener {
            startActivity(Intent(this, RecordMengejaKataActivity::class.java))
            finish()
        }
    }
}