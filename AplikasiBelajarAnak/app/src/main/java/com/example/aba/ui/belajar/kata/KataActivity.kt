package com.example.aba.ui.belajar.kata

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.aba.data.api.ApiConfig
import com.example.aba.data.model.KataModel
import com.example.aba.data.response.RimaKataResponse
import com.example.aba.databinding.ActivityKataBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKataBinding
    private lateinit var auth: FirebaseAuth

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

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Get Token then get Kata
        refreshKata()

        binding.btRefresh.setOnClickListener {
            refreshKata()
        }
    }

    private fun setListKataGampang(listKataGampang: ArrayList<KataModel>) {
        //mudah
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

    private fun setListKataSedang(listKataSedang: ArrayList<KataModel>){
        //sedang
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
    private fun setListKataSulit(listKataSulit: ArrayList<KataModel>){
        //sulit
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

    private fun refreshKata(){
        val user = auth.currentUser
        user!!.getIdToken(true)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val idToken: String? = task.result.token
                    Log.d("token di login",idToken!!)

                    //showLoading(true)
                    getKataFromAPI(idToken)

                    // Send token to your backend via HTTPS
                    // ...
                } else {
                    // Handle error -> task.getException();
                }
            }
    }
    private fun getKataFromAPI(token: String) {
//        showLoading(true)
        val auth = "Bearer $token"
        val client = ApiConfig().getApiService().cariRimaKata(auth)
        client.enqueue(object : Callback<RimaKataResponse> {
            override fun onResponse(
                call: Call<RimaKataResponse>,
                response: Response<RimaKataResponse>
            ) {
//                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d("test",responseBody.data.toString())

                        val jsonStringMudah = responseBody.data.mudah
                        val jsonStringSedang = responseBody.data.sedang
                        val jsonStringSulit = responseBody.data.sulit

//                        Log.d("test",jsonStringMudah)
//                        Log.d("test",jsonStringSulit)
                        setListKataGampang(jsonStringMudah)
                        setListKataSedang(jsonStringSedang)
                        setListKataSulit(jsonStringSulit)
                    }
                } else {
                    Log.e("gagal", "onFailure: ${response.raw()}")
                }
            }
            override fun onFailure(call: Call<RimaKataResponse>, t: Throwable) {
//                showLoading(false)
//                showNoUser(true)
                Log.e("gagal", "onFailure: ${t.message}")
            }
        })
    }
}