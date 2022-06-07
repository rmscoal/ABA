package com.example.aba

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.aba.data.model.KataModel
import com.example.aba.ui.belajar.kata.ListKataAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Func {

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

//    private fun getKataFromJson(context: Context = applicationContext, fileName: String): ArrayList<com.example.aba.data.response.KataModel>{
//        val filteredArray: ArrayList<com.example.aba.data.response.KataModel> = ArrayList()
//        val jsonString: String = context.assets.open(fileName).bufferedReader().use { it.readText() }
//
//        Log.i("data", jsonString)
//        val gson = Gson()
//        val listKataType = object : TypeToken<ArrayList<KataModel>>() {}.type
//
//        val kata = gson.fromJson(jsonString, listKataType) as ArrayList<com.example.aba.data.response.KataModel>
//        Log.i("ler", "$kata")
//        kata.forEachIndexed {
//                idx, it -> Log.i("data", "> Item $idx:\n$it")
//        }
//
//        for(i in kata){
//            if (filteredArray.size < 10){
//                filteredArray.add(i)
//            }
//            else break
//        }
//
//        return filteredArray
//    }

//    private fun setListKataSulit(listKataSulit: ArrayList<com.example.aba.data.response.KataModel>){
//        //sulit
//        val listKataSulit = getKataFromJson(fileName = "sulit.json")
//        Log.i("sulit",listKataSulit.size.toString())
//        binding.rvKataSulit.layoutManager = GridLayoutManager(this,3)
//        val kataSulit = ListKataAdapter(listKataSulit)
//        binding.rvKataSulit.adapter = kataSulit
//
//        kataSulit.setOnItemClickCallback(object : ListKataAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: com.example.aba.data.response.KataModel) {
//                showDetailKata(data)
//            }
//        })
//    }

//    kata.level1.forEachIndexed {
//        idx, it -> Log.i("data", "> Item $idx:\n$it")
//    }
}