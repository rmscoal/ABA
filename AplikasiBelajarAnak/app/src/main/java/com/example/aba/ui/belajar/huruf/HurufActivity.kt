package com.example.aba.ui.belajar.huruf

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aba.R
import com.example.aba.data.model.UserModel
import com.example.aba.databinding.ActivityHurufBinding
import com.example.aba.ui.home.HomeActivity
import com.example.aba.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HurufActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHurufBinding

    //firebase
    private lateinit var auth: FirebaseAuth

    //userModel
    private var userModel= UserModel()

    //mediaPlayer
    private lateinit var mPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHurufBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        //get userdata
        auth = Firebase.auth
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
//        val mUser = FirebaseAuth.getInstance().currentUser

        getToken()
        sendProgress()

        with(binding){
            hurufA.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.a)
                mPlayer.start()

                //ketika dipencet
            //  edit text bertambah
                // simpan huruf di user pref
                //
            }
            hurufB.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.b)
                mPlayer.start()
            }
            hurufC.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.c)
                mPlayer.start()
            }
            hurufD.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.d)
                mPlayer.start()
            }
            hurufE.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.e)
                mPlayer.start()
            }
            hurufF.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.f)
                mPlayer.start()
            }
            hurufG.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.g)
                mPlayer.start()
            }
            hurufH.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.h)
                mPlayer.start()
            }
            hurufI.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.i)
                mPlayer.start()
            }
            hurufJ.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.j)
                mPlayer.start()
            }
            hurufK.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.k)
                mPlayer.start()
            }
            hurufL.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.l)
                mPlayer.start()
            }
            hurufM.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.m)
                mPlayer.start()
            }
            hurufN.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.n)
                mPlayer.start()
            }
            hurufO.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.o)
                mPlayer.start()
            }
            hurufP.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.p)
                mPlayer.start()
            }
            hurufQ.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.q)
                mPlayer.start()
            }
            hurufR.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.r)
                mPlayer.start()
            }
            hurufS.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.s)
                mPlayer.start()
            }
            hurufT.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.t)
                mPlayer.start()
            }
            hurufU.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.u)
                mPlayer.start()
            }
            hurufV.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.v)
                mPlayer.start()
            }
            hurufW.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.w)
                mPlayer.start()
            }
            hurufX.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.x)
                mPlayer.start()
            }
            hurufY.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.y)
                mPlayer.start()
            }
            hurufZ.setOnClickListener {
                mPlayer = MediaPlayer.create(this@HurufActivity,R.raw.z)
                mPlayer.start()
            }


            //lainnya
            btBack.setOnClickListener {
                startActivity(Intent(this@HurufActivity,HomeActivity::class.java))
            }
        }

    }

    private fun sendProgress(){
        val token = userModel.token
        Log.d("token", "$token")
    }

    private fun getToken(){
        val user = auth.currentUser
        user!!.getIdToken(true)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val idToken: String? = task.result.token
//                    Log.d("token di login",idToken!!)
                    userModel.token = idToken!!
                } else {
                    // Handle error -> task.getException();
                }
            }
    }
}