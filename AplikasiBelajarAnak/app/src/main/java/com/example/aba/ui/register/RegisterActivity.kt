package com.example.aba.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.aba.databinding.ActivityRegisterBinding
import com.example.aba.ui.login.LoginActivity
import com.example.aba.ui.settings.SettingsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnRegister.setOnClickListener {
            val username = binding.etName.text.toString()
            val email = binding.cvEmail.text.toString()
            val password = binding.cvPassword.text.toString()
            Log.d(TAG, "${username}, ${email}, ${password}")
            registerUser(username,email,password)
        }
    }

    private fun registerUser(username: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    Log.d(TAG, "RegisterUser:success")
                    Toast.makeText(this@RegisterActivity,"Register Berhasil",Toast.LENGTH_SHORT).show()
                    getToken()
                    updateUI()
                }
                else{
                    Log.d(TAG, "RegisterUser:failed")
                }
            }
    }

    private fun getToken(){
        val mUser = FirebaseAuth.getInstance().currentUser
        mUser!!.getIdToken(true)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val idToken: String? = task.result.token
                    Log.d("token di register",idToken!!)
                    // Send token to your backend via HTTPS
                    // ...
                } else {
                    // Handle error -> task.getException();
                }
            }
    }

    private fun updateUI() {
        startActivity(Intent(this, SettingsActivity::class.java))
        finish()
    }

    companion object{
        private const val TAG = "RegisterActivity"
        private var token: String = ""
    }
}