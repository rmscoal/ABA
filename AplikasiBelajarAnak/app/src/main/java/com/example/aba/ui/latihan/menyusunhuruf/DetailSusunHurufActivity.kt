package com.example.aba.ui.latihan.menyusunhuruf

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.aba.R
import com.example.aba.data.model.KataModel
import com.example.aba.databinding.ActivityDetailKataBinding
import com.example.aba.databinding.ActivityDetailSusunHurufBinding
import com.example.aba.databinding.ActivityLoginBinding
import com.example.aba.databinding.ActivitySusunHurufBinding
import com.example.aba.ui.belajar.kata.DetailKataActivity
import com.example.aba.ui.belajar.kata.ListKataAdapter
import com.example.aba.ui.latihan.HasilRecordAudioActivity
import com.example.aba.ui.latihan.HasilRecordAudioActivity2

class DetailSusunHurufActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSusunHurufBinding
    companion object{
        private var LEMA = "lema"
        private var URL = "url"
        private var RESULT = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSusunHurufBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get lema and url from intent
        val lemaFromIntent = intent.getStringExtra(LEMA)
        val urlFromIntent = intent.getStringExtra(URL)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvHuruf.layoutManager = layoutManager
        binding.rvHuruf.addItemDecoration(itemDecoration)

        //set kata and avatar
        binding.tvLema.text = lemaFromIntent
        if (URL !="NA"){
            Glide.with(this)
                .load(urlFromIntent)
                .fitCenter()
                .into(binding.ivAvatar)
        }
        binding.btRefresh.setOnClickListener {
            resetWadah()
        }

        val arraylistLema = ArrayList<Char>()
        for(char in lemaFromIntent!!){
            arraylistLema.add(char)
        }
        setHuruf(arraylistLema)

        binding.btHasil.setOnClickListener {
            if (RESULT == lemaFromIntent){
                startActivity(Intent(this,HasilRecordAudioActivity::class.java))
            }
            else startActivity(Intent(this,HasilRecordAudioActivity2::class.java))
        }
    }

    private fun resetWadah() {
        RESULT = ""
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    private fun setHuruf(listHuruf: ArrayList<Char>) {

        Log.i("gampang", listHuruf.size.toString())

        val shuffledListHuruf = listHuruf.shuffled() as ArrayList<Char>

        val huruf = DetailSusunHurufAdapter(shuffledListHuruf)
        binding.rvHuruf.adapter = huruf

        huruf.setOnItemClickCallback(object : DetailSusunHurufAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Char) {
                updateWadah(data)
            }
        })
    }

    private fun updateWadah(data: Char) {
        RESULT += data
        binding.tvWadah.text = RESULT
    }

    override fun onBackPressed() {
        super.onBackPressed()
        RESULT = ""
    }
}