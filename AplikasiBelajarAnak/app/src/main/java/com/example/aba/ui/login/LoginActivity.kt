package com.example.aba.ui.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.beust.klaxon.Json
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.example.aba.R
import com.example.aba.data.api.ApiConfig
import com.example.aba.data.preferences.EksplorAngka
import com.example.aba.data.preferences.EksplorHuruf
import com.example.aba.data.preferences.UserModel
import com.example.aba.data.preferences.UserPreferences
import com.example.aba.data.response.UserResponse
import com.example.aba.databinding.ActivityLoginBinding
import com.example.aba.ui.home.HomeActivity
import com.example.aba.ui.settings.SettingsActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var userPreferences: UserPreferences
    private lateinit var userModel: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configure Google Sign In
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        binding.signInButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }
    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    user!!.getIdToken(true)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val idToken: String? = task.result.token
                            Log.d("token di login",idToken!!)

                            //showLoading(true)
                            getUserData(idToken!!)

                            // Send token to your backend via HTTPS
                            // ...
                        } else {
                            // Handle error -> task.getException();
                        }
                    }
                    //showLoading(false)
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun getUserData(token: String) {
//        showLoading(true)
        val auth = "Bearer $token"
        Log.e(TAG, "token: ${auth}")
        val client = ApiConfig().getApiService().getDataUser(auth)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
//                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d("respon",responseBody.data.toString())

                        //save user data to user preferences
                        val data = responseBody.data
                        with(userModel){
                            name = data.namaUser
                            achv_id = data.achvId
                            eksplor_angka = data.eksplorAngka
                            eksplor_huruf = data.eksplorHuruf
                            latMengejaHuruflv1 = data.latMengejaHuruflvl1
                            latMengejaHuruflv2 = data.latMengejaHuruflvl2
                            latMengejaHuruflv3 = data.latMengejaHuruflvl3
                            latMenyusunKatalvl1 = data.latMenyusunKatalvl1
                            latMenyusunKatalvl2 = data.latMenyusunKatalvl2
                            latMenyusunKatalvl3 = data.latMenyusunKatalvl3
                        }
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.raw()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
//                showLoading(false)
//                showNoUser(true)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null){
            startActivity(Intent(this, SettingsActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    companion object {
        private const val TAG = "LoginActivity"
        private var token: String = ""
    }

    private fun parseEksplorHuruftoJSON(){
        val eksplorHuruf = userModel.eksplor_huruf
        val hasil = Klaxon().parse<EksplorHuruf>(
//            """
//            {
//            "status":"success",
//            "message":"Query user data successfully done!",
//            "data":
//                    {
//                        "nama_user":"rifky.satyana08",
//                        "achv_id":16,
//                        "user_id":19,
//                        "eksplor_huruf":"{\"a\": true, \"b\": true, \"c\": true}",
//                        "eksplor_angka":"{\"nol\": true, \"satu\": true, \"dua\": true}",
//                        "latMenyusunKatalvl1":5,
//                        "latMenyusunKatalvl2":5,
//                        "latMenyusunKatalvl3":0,
//                        "latMengejaHuruflvl1":5,
//                        "latMengejaHuruflvl2":5,
//                        "latMengejaHuruflvl3":0
//                    }
//            }
//            """.trimIndent()
//            )
        eksplorHuruf!!.trimIndent())

        Log.d("a","${hasil?.a}")
        Log.d("a","${hasil?.b}")
//        assert(hasil?.a == true)
//        assert(hasil?.b == false)
    }

    private fun userProgress(){

    }
}