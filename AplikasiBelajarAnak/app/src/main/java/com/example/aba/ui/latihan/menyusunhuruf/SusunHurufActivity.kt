package com.example.aba.ui.latihan.menyusunhuruf

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.aba.data.model.LatihanMenyusunHurufModel
import com.example.aba.data.model.KataModel
import com.example.aba.databinding.ActivitySusunHurufBinding
import com.example.aba.ui.belajar.kata.ListKataAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SusunHurufActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySusunHurufBinding
    companion object{
        private var LEMA = "lema"
        private var NILAI = "nilai"
        private var URL = "url"
        private const val FILENAME= "latihan_menyusun_huruf.json"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySusunHurufBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val layoutManager1 = GridLayoutManager(this,3)
        val layoutManager2 = GridLayoutManager(this,3)
        val layoutManager3 = GridLayoutManager(this,3)

        //Level1
        binding.rvLevel1.layoutManager = layoutManager1
        binding.rvLevel2.layoutManager = layoutManager2
        binding.rvLevel3.layoutManager = layoutManager3

        setListKataLevel1()
        setListKataLevel2()
        setListKataLevel3()

    }
    private fun setListKataLevel1() {
        //Level1
        val listKatalv1 = getKataFromJson(fileName = FILENAME, level = "level1")
        val katalv1 = ListKataAdapter(listKatalv1)
        binding.rvLevel1.adapter = katalv1

        katalv1.setOnItemClickCallback(object : ListKataAdapter.OnItemClickCallback {
            override fun onItemClicked(data: KataModel) {
                showDetailSusunHuruf(data)
            }
        })
    }
    private fun setListKataLevel2() {
        //Level2
        val listKatalv2 = getKataFromJson(fileName = FILENAME,level = "level2")
        val katalv2 = ListKataAdapter(listKatalv2)
        binding.rvLevel2.adapter = katalv2

        katalv2.setOnItemClickCallback(object : ListKataAdapter.OnItemClickCallback {
            override fun onItemClicked(data: KataModel) {
                showDetailSusunHuruf(data)
            }
        })
    }
    private fun setListKataLevel3() {
        //Level3
        val listKatalv3 = getKataFromJson(fileName = FILENAME, level = "level3")
        val katalv3 = ListKataAdapter(listKatalv3)
        binding.rvLevel3.adapter = katalv3

        katalv3.setOnItemClickCallback(object : ListKataAdapter.OnItemClickCallback {
            override fun onItemClicked(data: KataModel) {
                showDetailSusunHuruf(data)
            }
        })
    }


    private fun getKataFromJson(context: Context = applicationContext, fileName: String, level: String): ArrayList<KataModel>{
        val filteredArray: ArrayList<KataModel> = ArrayList()
        val jsonString: String = context.assets.open(fileName).bufferedReader().use { it.readText() }

        Log.i("data", jsonString)
        val gson = Gson()
        val listKataType = object : TypeToken<LatihanMenyusunHurufModel>() {}.type

        val kata = gson.fromJson(jsonString, listKataType) as LatihanMenyusunHurufModel
        Log.i("ler", "$kata")

        if (level=="level1"){
            for(i in kata.level1){
                if (filteredArray.size < 10){
                    filteredArray.add(i)
                }
                else break
            }
        }
        else if(level=="level2"){
            for(i in kata.level2){
                if (filteredArray.size < 10){
                    filteredArray.add(i)
                }
                else break
            }
        }
        else {
            for(i in kata.level3){
                if (filteredArray.size < 10){
                    filteredArray.add(i)
                }
                else break
            }
        }

        return filteredArray
    }

    private fun showDetailSusunHuruf(data: KataModel){
        val intentToDetail = Intent(this, DetailSusunHurufActivity::class.java)
        intentToDetail.putExtra(LEMA, data.lema)
        intentToDetail.putExtra(NILAI, data.nilai)
        intentToDetail.putExtra(URL, data.url)
        startActivity(intentToDetail,
            ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
    }
}