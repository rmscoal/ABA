package com.example.aba.ui.belajar.kata

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.aba.data.api.ApiConfig
import com.example.aba.data.model.KataModel
import com.example.aba.data.response.RimaKataResponse
import com.example.aba.databinding.ActivityBelajarKataBinding
import com.example.aba.ui.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBelajarKataBinding
    private lateinit var auth: FirebaseAuth

    companion object{
        private var LEMA = "lema"
        private var NILAI = "nilai"
        private var URL = "url"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBelajarKataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        showLoading(false)

        //mudah
        binding.rvKataMudah.layoutManager = GridLayoutManager(this,4)

        //sedang
        binding.rvKataSedang.layoutManager = GridLayoutManager(this,3)

        //sulit
        binding.rvKataSulit.layoutManager = GridLayoutManager(this,2)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Get Token then get Kata
        refreshKata()

        binding.btRefresh.setOnClickListener {
            refreshKata()
        }

        binding.btBack.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }
    }

    private fun setListKataGampang(listKataGampang: ArrayList<KataModel>) {
        //mudah
        Log.i("gampang",listKataGampang.size.toString())

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

                    showLoading(true)
                    getKataFromAPI(idToken)

                    // Send token to your backend via HTTPS
                    // ...
                } else {
                    // Handle error -> task.getException();
                }
            }
    }

    private fun showLoading(b: Boolean) {
        if (b) {
            binding.progressbar.visibility = View.VISIBLE
            binding.btRefresh.visibility = View.GONE
        } else {
            binding.progressbar.visibility = View.GONE
            binding.btRefresh.visibility = View.VISIBLE
        }
    }

    private fun getKataFromAPI(token: String) {
        showLoading(true)
        val auth = "Bearer $token"
        val client = ApiConfig().getApiService().cariRimaKata(auth)
        client.enqueue(object : Callback<RimaKataResponse> {
            override fun onResponse(
                call: Call<RimaKataResponse>,
                response: Response<RimaKataResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d("test",responseBody.data.toString())

                        val jsonStringMudah = responseBody.data.mudah
                        val jsonStringSedang = responseBody.data.sedang
                        val jsonStringSulit = responseBody.data.sulit

                        setListKataGampang(jsonStringMudah)
                        setListKataSedang(jsonStringSedang)
                        setListKataSulit(jsonStringSulit)
                    }
                } else {
                    Log.e("gagal", "onFailure: ${response.raw()}")
                }
            }
            override fun onFailure(call: Call<RimaKataResponse>, t: Throwable) {
                showLoading(false)
                Log.e("gagal", "onFailure: ${t.message}")
            }
        })
    }
}