package com.example.aba.ui.belajar.kata

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.aba.data.preferences.KataModel
import com.example.aba.databinding.ActivityKataBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class KataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKataBinding
    companion object{
        private var LEMA = "lema"
        private var NILAI = "nilai"
        private var URL = "url"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        val layoutManager1 = GridLayoutManager(this,4)
        val layoutManager2 = GridLayoutManager(this,4)
        val layoutManager3 = GridLayoutManager(this,4)
        val itemDecoration1 = DividerItemDecoration(this, layoutManager1.orientation)
        val itemDecoration2 = DividerItemDecoration(this, layoutManager2.orientation)
        val itemDecoration3 = DividerItemDecoration(this, layoutManager3.orientation)

        //mudah
        binding.rvKataMudah.layoutManager = layoutManager1
        binding.rvKataMudah.addItemDecoration(itemDecoration1)

        //sedang
        binding.rvKataSedang.layoutManager = layoutManager2
        binding.rvKataSedang.addItemDecoration(itemDecoration2)

        //sulit
        binding.rvKataSulit.layoutManager = layoutManager3
        binding.rvKataSulit.addItemDecoration(itemDecoration3)

        setListKataGampang()
        setListKataSedang()
        setListKataSulit()
    }

    private fun setListKataGampang() {
        //mudah
        val listKataGampang = getKataFromJson(fileName = "gampang.json")
        Log.i("gampang",listKataGampang.size.toString())
        binding.rvKataMudah.layoutManager = GridLayoutManager(this,4)
        val kataGampang = ListKataAdapter(listKataGampang)
        binding.rvKataMudah.adapter = kataGampang

       kataGampang.setOnItemClickCallback(object : ListKataAdapter.OnItemClickCallback {
            override fun onItemClicked(data: KataModel) {
                showDetailKata(data)
            }
        })
    }

    private fun setListKataSedang(){
        //sedang
        val listKataSedang = getKataFromJson(fileName = "sedang.json")
        Log.i("array",listKataSedang.toString())
        binding.rvKataSedang.layoutManager = GridLayoutManager(this,3)
        val kataSedang = ListKataAdapter(listKataSedang)
        binding.rvKataSedang.adapter = kataSedang

        kataSedang.setOnItemClickCallback(object : ListKataAdapter.OnItemClickCallback {
            override fun onItemClicked(data: KataModel) {
                showDetailKata(data)
            }
        })
    }
    private fun setListKataSulit(){
        //sulit
        val listKataSulit = getKataFromJson(fileName = "sulit.json")
        Log.i("sulit",listKataSulit.size.toString())
        binding.rvKataSulit.layoutManager = GridLayoutManager(this,3)
        val kataSulit = ListKataAdapter(listKataSulit)
        binding.rvKataSulit.adapter = kataSulit

        kataSulit.setOnItemClickCallback(object : ListKataAdapter.OnItemClickCallback {
            override fun onItemClicked(data: KataModel) {
                showDetailKata(data)
            }
        })
    }
    private fun showDetailKata(data: KataModel){
        val intentToDetail = Intent(this, DetailKataActivity::class.java)
        intentToDetail.putExtra(LEMA, data.lema)
        intentToDetail.putExtra(NILAI, data.nilai)
        intentToDetail.putExtra(URL, data.url)
        startActivity(intentToDetail,
            ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
    }
//    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
//        val jsonString: String
//        try {
//            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
//        } catch (ioException: IOException) {
//            ioException.printStackTrace()
//            return null
//        }
//        return jsonString
//    }

    private fun getKataFromJson(context:Context = applicationContext,fileName: String): ArrayList<KataModel>{
        val filteredArray: ArrayList<KataModel> = ArrayList()
        val jsonString: String = context.assets.open(fileName).bufferedReader().use { it.readText() }

        Log.i("data", jsonString)
        val gson = Gson()
        val listKataType = object : TypeToken<ArrayList<KataModel>>() {}.type

        val kata = gson.fromJson(jsonString, listKataType) as ArrayList<KataModel>
        Log.i("ler", "$kata")
        kata.forEachIndexed {
                idx, it -> Log.i("data", "> Item $idx:\n$it")
        }

        for(i in kata){
            if (filteredArray.size < 10){
                filteredArray.add(i)
            }
            else break
        }

        return filteredArray
    }

}