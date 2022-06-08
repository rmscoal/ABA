package com.example.aba.ui.latihan.menyusunhuruf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.example.aba.R
import com.example.aba.databinding.ActivityHasilMenyusunHurufBinding
import com.example.aba.databinding.ActivityHasilRecordMengejaHurufBinding
import com.example.aba.ui.latihan.mengejahuruf.RecordMengejaHurufActivity

class HasilMenyusunHurufActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHasilMenyusunHurufBinding
    companion object{
        private const val RESULT = "RESULT"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHasilMenyusunHurufBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            startActivity(Intent(this, DetailSusunHurufActivity::class.java))
            finish()
        }
    }

}