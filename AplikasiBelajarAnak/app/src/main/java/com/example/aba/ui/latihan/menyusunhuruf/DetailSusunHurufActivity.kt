package com.example.aba.ui.latihan.menyusunhuruf

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.aba.databinding.ActivityDetailSusunHurufBinding
import com.example.aba.ui.latihan.mengejahuruf.HasilRecordMengejaHurufActivity

class DetailSusunHurufActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSusunHurufBinding
    companion object{
        private var LEMA = "lema"
        private var URL = "url"
        private var SUSUN = ""
        private const val RESULT = "RESULT"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSusunHurufBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get lema and url from intent
        val lemaFromIntent = intent.getStringExtra(LEMA)
        val urlFromIntent = intent.getStringExtra(URL)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.rvHuruf.layoutManager = layoutManager

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
            val i = Intent(this, HasilMenyusunHurufActivity::class.java)
            if (SUSUN == lemaFromIntent){
                i.putExtra(RESULT,true)
                startActivity(i)
            }
            else {
                i.putExtra(RESULT,false)
                startActivity(i)
            }
        }
    }

    private fun resetWadah() {
        SUSUN = ""
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
        SUSUN += data
        binding.tvWadah.text = SUSUN
    }

    override fun onBackPressed() {
        super.onBackPressed()
        SUSUN = ""
    }
}