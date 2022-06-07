package com.example.aba.ui.latihan.mengejakata

import android.content.Intent
import android.media.AudioFormat
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.aba.data.api.ApiConfig
import com.example.aba.data.model.UserModel
import com.example.aba.data.response.HurufRecordingResponse
import com.example.aba.databinding.ActivityRecordMengejaKataBinding
import com.example.aba.ui.latihan.HasilRecordAudioActivity
import com.example.aba.ui.latihan.HasilRecordAudioActivity2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import kotlin.math.roundToInt

class RecordMengejaKataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecordMengejaKataBinding
    private lateinit var auth: FirebaseAuth
    private var userModel= UserModel()
    private var output: String? = null
    private var mediaRecorder: MediaRecorder? = null
    private var state: Boolean = false

    private var getFile: File? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordMengejaKataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set user
        auth = Firebase.auth

        //create directory in folder ABA
        val folder = Environment.getExternalStorageDirectory().toString()
        val f = File(folder,"ABA")
        f.mkdir()

        ///storage/emulated/0/ABA/test.wav
        output = Environment.getExternalStorageDirectory().toString() + "/ABA/test.m4a"
        Log.d("path","$output")

        //set file to upload
        getFile = File(output!!)

//        binding.btStartRecord.setOnClickListener {
//            if (ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
//                ActivityCompat.requestPermissions(this, permissions,0)
//            } else {
//                startRecording()
//            }
//        }
//
//        binding.btStopRecord.setOnClickListener{
//            stopRecording()
//        }
//
//        binding.btPlayRecord.setOnClickListener {
//            playRecording()
//        }
//
//        binding.tvLihat.setOnClickListener {
//            val user = auth.currentUser
//            user!!.getIdToken(true)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        val idToken: String? = task.result.token
//                        Log.d("token di login",idToken!!)
//                        //showLoading(true)
//                        uploadRecording(idToken!!)
//                        // Send token to your backend via HTTPS
//                        // ...
//                    } else {
//                        // Handle error -> task.getException();
//                    }
//                }
//        }
    }

    private fun startRecording() {
        try {
            mediaRecorder = MediaRecorder()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder?.setOutputFormat(AudioFormat.ENCODING_PCM_16BIT);
                mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                mediaRecorder?.setAudioChannels(2)
                mediaRecorder?.setOutputFile(output)

            }
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            state = true
            Toast.makeText(this, "Recording started!", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopRecording(){
        if(state){
            mediaRecorder?.stop()
            mediaRecorder?.reset()
            state = false
            Toast.makeText(this, "Recording Stopped", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "You are not recording right now!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun playRecording(){
        val mp = MediaPlayer()
        mp.setDataSource(output)
        mp.prepare()
        mp.start()
    }


    private fun uploadRecording(token: String) {
        if (getFile != null) {

            val file = getFile as File

            val requestAudioFile = file.asRequestBody("audio/mp4".toMediaTypeOrNull())
            Log.d("audio","$requestAudioFile")
            val audioMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "",
                file.name,
                requestAudioFile
            )
            val huruf = "a"
            val auth = "Bearer $token"
            Log.d("filename","$file")
            val service = ApiConfig().getApiService().hurufRecording(auth,huruf,audioMultipart)
            service.enqueue(object : Callback<HurufRecordingResponse> {
                override fun onResponse(
                    call: Call<HurufRecordingResponse>,
                    response: Response<HurufRecordingResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            Log.d("responUpload",responseBody.toString())
                            updateUI(responseBody)
                            //Toast.makeText(this@RecordMengejaHurufActivity, resources.getString(R.string.berhasil_upload), Toast.LENGTH_SHORT).show()
                            //showLoading(false)
                            //startActivity(Intent(this@RecordMengejaHurufActivity, ListStoryActivity::class.java))
                            //finish()
                        }
                    } else {
                        Toast.makeText(this@RecordMengejaKataActivity, response.message(), Toast.LENGTH_SHORT).show()
                        Log.d("gagalff",response.message())
                        //showLoading(false)
                    }
                }
                override fun onFailure(call: Call<HurufRecordingResponse>, t: Throwable) {
                    Log.d("gagal",t.localizedMessage)
                    //Toast.makeText(this@RecordMengejaHurufActivity, resources.getString(R.string.gagal), Toast.LENGTH_SHORT).show()
                    //showLoading(false)
                }
            })
        } else {
            //Toast.makeText(this@AddStoryActivity, resources.getString(R.string.masukkan), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(responseBody: HurufRecordingResponse) {
        if(responseBody.result?.roundToInt() == 1 ){
            startActivity(Intent(this, HasilRecordAudioActivity::class.java))
        }
        else startActivity(Intent(this, HasilRecordAudioActivity2::class.java))
    }

}